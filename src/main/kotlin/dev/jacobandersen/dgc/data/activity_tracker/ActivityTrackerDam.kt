package dev.jacobandersen.dgc.data.activity_tracker

import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import com.github.benmanes.caffeine.cache.RemovalListener
import dev.jacobandersen.dgc.manager.DataManager
import java.util.concurrent.TimeUnit

class ActivityTrackerDam(private val manager: DataManager) {
    private val dao = manager.jdbi.onDemand(ActivityTrackerDao::class.java)
    private val cache: LoadingCache<Long, ActivityTracker> = Caffeine
        .newBuilder()
        .expireAfterAccess(15, TimeUnit.MINUTES)
        .removalListener(RemovalListener<Long, ActivityTracker> { key, value, cause ->
            if (key == null || value == null) return@RemovalListener

            if (cause.wasEvicted()) {
                dao.updateByUserId(
                    value.userId,
                    value.lastActivity,
                    value.lastMessageId,
                    value.totalMessages
                )
            }
        })
        .build { key -> dao.findByUserId(key) }

    fun createContainer() {
        dao.createContainer()
    }

    fun createByUserId(userId: Long) {
        dao.createByUserId(userId)
    }

    fun findByUserId(userId: Long): ActivityTracker? {
        return cache.get(userId)
    }

    fun updateByUserId(userId: Long, mutator: (tracker: ActivityTracker) -> Unit) {
        var tracker = findByUserId(userId)

        if (tracker == null) {
            tracker = ActivityTracker(userId)
            cache.put(userId, tracker)
        }

        mutator(tracker)
    }

    fun deleteByUserId(userId: Long) {
        dao.deleteByUserId(userId)
    }
}
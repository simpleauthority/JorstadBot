package dev.jacobandersen.dgc.data.activity_tracker

import org.javacord.api.event.message.MessageCreateEvent
import org.javacord.api.event.message.MessageDeleteEvent
import org.javacord.api.listener.server.member.ServerMemberJoinListener
import java.sql.Timestamp
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

data class ActivityTracker(
    val userId: Long,
    var lastActivity: Timestamp,
    var lastMessageId: Long,
    var totalMessages: Long
) {
    constructor(userId: Long) : this(userId, Timestamp.from(Instant.now()), -1, 0) {
        updateActivity()
    }

    fun track(event: MessageCreateEvent) {
        lastMessageId = event.messageId
        totalMessages++
        updateActivity()
    }

    fun track(event: MessageDeleteEvent) {
        totalMessages--
        updateActivity()
    }

    fun track(event: ServerMemberJoinListener) {
        updateActivity()
    }

    private fun updateActivity() {
        lastActivity = Timestamp.from(Instant.now(Clock.system(ZoneId.systemDefault())))
    }
}

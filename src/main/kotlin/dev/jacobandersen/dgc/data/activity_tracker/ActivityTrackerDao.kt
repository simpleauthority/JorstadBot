package dev.jacobandersen.dgc.data.activity_tracker

import org.jdbi.v3.sqlobject.config.RegisterRowMapper
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import java.sql.Timestamp

interface ActivityTrackerDao {
    @SqlUpdate("create table if not exists activity_tracker (user_id integer not null, last_activity timestamp not null, last_message_id integer not null, total_messages integer not null, constraint activity_tracker_pk primary key (user_id));")
    fun createContainer()

    @SqlUpdate("insert into activity_tracker(user_id, last_activity, last_message_id, total_messages) values (:user_id, current_timestamp, -1, 0)")
    fun createByUserId(userId: Long)

    @SqlQuery("select * from activity_tracker where user_id = :user_id")
    @RegisterRowMapper(ActivityTrackerMapper::class)
    fun findByUserId(@Bind("user_id") userId: Long): ActivityTracker

    @SqlUpdate("update activity_tracker set last_activity = :last_activity, last_message_id = :last_message_id, total_messages = :total_messages where user_id = :user_id")
    fun updateByUserId(
        @Bind("user_id") userId: Long,
        @Bind("last_activity") lastActivity: Timestamp,
        @Bind("last_message_id") lastMessageId: Long,
        @Bind("total_messages") totalMessages: Long
    )

    @SqlUpdate("delete from activity_tracker where user_id = :user_id")
    fun deleteByUserId(@Bind("user_id") userId: Long)
}
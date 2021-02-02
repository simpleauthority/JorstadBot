package dev.jacobandersen.dgc.data.activity_tracker

import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import java.sql.ResultSet

class ActivityTrackerMapper : RowMapper<ActivityTracker> {
    override fun map(rs: ResultSet, ctx: StatementContext): ActivityTracker {
        return ActivityTracker(
            rs.getLong("user_id"),
            rs.getTimestamp("last_activity"),
            rs.getLong("last_message_id"),
            rs.getLong("total_messages")
        )
    }
}
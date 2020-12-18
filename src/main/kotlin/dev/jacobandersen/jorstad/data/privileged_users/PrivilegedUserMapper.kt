package dev.jacobandersen.jorstad.data.privileged_users

import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import java.sql.ResultSet

class PrivilegedUserMapper : RowMapper<PrivilegedUser> {
    override fun map(rs: ResultSet, ctx: StatementContext): PrivilegedUser {
        return PrivilegedUser(
            rs.getInt("id"),
            rs.getLong("guild_id"),
            rs.getLong("user_id"),
            rs.getString("privileges").split(",").map { PrivilegedUser.Privilege.fromString(it) }
        )
    }
}
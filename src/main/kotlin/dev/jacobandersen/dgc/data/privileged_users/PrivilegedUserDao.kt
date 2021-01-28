package dev.jacobandersen.dgc.data.privileged_users

import org.jdbi.v3.sqlobject.config.RegisterRowMapper
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface PrivilegedUserDao {
    @SqlUpdate("create table if not exists privileged_users (id integer not null, guild_id integer not null, user_id integer not null, privileges text not null, constraint privileged_users_pk primary key (id autoincrement));")
    fun createContainer()

    @SqlQuery("select exists(select 1 from privileged_users where guild_id = :guild_id and user_id = :user_id)")
    fun privilegedUserExists(@Bind("guild_id") guildId: Long, @Bind("user_id") userId: Long): Boolean

    @SqlUpdate("insert into privileged_users(guild_id, user_id, privileges) values (:guild_id, :user_id, :privileges)")
    fun addPrivilegedUser(
        @Bind("guild_id") guildId: Long,
        @Bind("user_id") userId: Long,
        @Bind("privileges") privileges: String
    )

    @SqlQuery("select * from privileged_users where guild_id = :guild_id and user_id = :user_id limit 1")
    @RegisterRowMapper(PrivilegedUserMapper::class)
    fun findPrivilegedUser(@Bind("guild_id") guildId: Long, @Bind("user_id") userId: Long): PrivilegedUser?

    @SqlUpdate("update privileged_users set privileges = :privileges where guild_id = :guild_id and user_id = :user_id")
    fun updatePrivilegedUser(
        @Bind("guild_id") guildId: Long,
        @Bind("user_id") userId: Long,
        @Bind("privileges") privileges: String
    )

    @SqlUpdate("delete from privileged_users where guild_id = :guild_id and user_id = :user_id")
    fun deletePrivilegedUser(@Bind("guild_id") guildId: Long, @Bind("user_id") userId: Long)

    @SqlUpdate("delete from privileged_users where guild_id = :guild_id")
    fun deletePrivilegedUsers(@Bind("guild_id") guildId: Long)
}
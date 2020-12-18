package dev.jacobandersen.jorstad.data.text_commands

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.kotlin.RegisterKotlinMapper
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface TextCommandDao {
    @SqlUpdate("create table if not exists text_commands (guild_id integer not null, name varchar(16) not null, output text not null, constraint text_commands_pk primary key (guild_id, name));")
    fun createContainer()

    @SqlQuery("select exists(select 1 from text_commands where guild_id = :guild_id and name = :name)")
    fun textCommandExists(@Bind("guild_id") guildId: Long, @Bind("name") name: String): Boolean

    @SqlUpdate("insert into text_commands(guild_id, name, output) values (:guild_id, :name, :output)")
    fun addTextCommand(@Bind("guild_id") guildId: Long, @Bind("name") name: String, @Bind("output") output: String)

    @SqlQuery("select * from text_commands where guild_id = :guild_id and name = :name limit 1")
    @RegisterKotlinMapper(TextCommand::class)
    fun findTextCommand(@Bind("guild_id") guildId: Long, @Bind("name") name: String): TextCommand?

    @SqlQuery("select * from text_commands where guild_id = :guild_id")
    @RegisterKotlinMapper(TextCommand::class)
    fun findTextCommands(@Bind("guild_id") guildId: Long): List<TextCommand>

    @SqlUpdate("delete from text_commands where guild_id = :guild_id and name = :name")
    fun deleteTextCommand(@Bind("guild_id") guildId: Long, @Bind("name") name: String)
}
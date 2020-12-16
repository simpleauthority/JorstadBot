package dev.jacobandersen.jorstad.data.textcommands

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.kotlin.RegisterKotlinMapper
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface TextCommandDao {
    @SqlUpdate(
        "create table if not exists text_commands (\n" +
                "id integer,\n" +
                "name varchar(16) not null,\n" +
                "output text not null,\n" +
                "constraint text_commands_pk\n" +
                "primary key (id autoincrement)\n" +
                ");"
    )
    fun createContainer()

    @SqlQuery("select exists(select 1 from text_commands where name = :name)")
    fun nameExists(@Bind("name") name: String): Boolean

    @SqlUpdate("insert into text_commands(name, output) values (:name, :output)")
    fun addTextCommand(name: String, output: String)

    @SqlQuery("select * from text_commands where name = :name limit 1")
    @RegisterKotlinMapper(TextCommand::class)
    fun findTextCommand(name: String): TextCommand

    @SqlQuery("select * from text_commands")
    @RegisterKotlinMapper(TextCommand::class)
    fun findTextCommands(): List<TextCommand>

    @SqlUpdate("delete from text_commands where name = :name")
    fun deleteTextCommand(name: String)
}
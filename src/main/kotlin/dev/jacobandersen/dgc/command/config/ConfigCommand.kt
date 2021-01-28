package dev.jacobandersen.dgc.command.config

import cloud.commandframework.Command
import cloud.commandframework.javacord.JavacordCommandManager
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.dgc.DgcBot
import dev.jacobandersen.dgc.command.api.BasicCommand
import dev.jacobandersen.dgc.data.privileged_users.PrivilegedUser

class ConfigCommand(private val bot: DgcBot) : BasicCommand() {
    override fun construct(manager: JavacordCommandManager<JavacordCommandSender>): List<Command<JavacordCommandSender>> {
        val builder = manager
            .commandBuilder("config")
            .permission(PrivilegedUser.Privilege.CONFIG_COMMAND.permission())

        val configGet = ConfigGetCommand(bot).terminal(builder).build()
        val configSet = ConfigSetCommand(bot).terminal(builder).build()
        val configUnset = ConfigUnsetCommand(bot).terminal(builder).build()

        return listOf(configGet, configSet, configUnset)
    }
}
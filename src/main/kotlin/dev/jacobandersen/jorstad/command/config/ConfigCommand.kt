package dev.jacobandersen.jorstad.command.config

import cloud.commandframework.Command
import cloud.commandframework.javacord.JavacordCommandManager
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.jorstad.JorstadBot
import dev.jacobandersen.jorstad.command.api.BasicCommand
import dev.jacobandersen.jorstad.data.privileged_users.PrivilegedUser

class ConfigCommand(private val bot: JorstadBot) : BasicCommand() {
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
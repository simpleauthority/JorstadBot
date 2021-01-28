package dev.jacobandersen.dgc.command.factoid

import cloud.commandframework.Command
import cloud.commandframework.javacord.JavacordCommandManager
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.dgc.DgcBot
import dev.jacobandersen.dgc.command.api.BasicCommand
import dev.jacobandersen.dgc.data.privileged_users.PrivilegedUser

class FactoidCommand(private val bot: DgcBot) : BasicCommand() {
    override fun construct(manager: JavacordCommandManager<JavacordCommandSender>): List<Command<JavacordCommandSender>> {
        val base = manager
            .commandBuilder("factoid", "fact")
            .permission(PrivilegedUser.Privilege.FACTOID_COMMAND.permission())

        val factoidAddCommand = FactoidAddCommand(bot).terminal(base).build()
        val factoidDelCommand = FactoidDelCommand(bot).terminal(base).build()

        return listOf(factoidAddCommand, factoidDelCommand)
    }
}
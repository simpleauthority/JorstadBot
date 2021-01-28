package dev.jacobandersen.dgc.command.role.remove

import cloud.commandframework.Command
import cloud.commandframework.arguments.StaticArgument
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.dgc.DgcBot
import dev.jacobandersen.dgc.command.api.BaseSubcommand

class RoleRemoveFromCommand(private val bot: DgcBot) : BaseSubcommand {
    override fun children(builder: Command.Builder<JavacordCommandSender>): List<Command.Builder<JavacordCommandSender>> {
        val base = builder
            .argument(StaticArgument.of("remove"))
            .argument(StaticArgument.of("from"))

        val removeFromAll = RoleRemoveFromAllCommand(bot).terminal(base)
        val removeFromUser = RoleRemoveFromUserCommand(bot).terminal(base)

        return listOf(removeFromAll, removeFromUser)
    }
}
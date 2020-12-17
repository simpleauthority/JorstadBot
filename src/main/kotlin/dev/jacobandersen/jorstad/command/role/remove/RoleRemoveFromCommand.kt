package dev.jacobandersen.jorstad.command.role.remove

import cloud.commandframework.Command
import cloud.commandframework.arguments.StaticArgument
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.jorstad.JorstadBot
import dev.jacobandersen.jorstad.command.api.BaseSubcommand

class RoleRemoveFromCommand(private val bot: JorstadBot) : BaseSubcommand {
    override fun children(builder: Command.Builder<JavacordCommandSender>): List<Command.Builder<JavacordCommandSender>> {
        val base = builder
            .argument(StaticArgument.of("remove"))
            .argument(StaticArgument.of("from"))

        val removeFromAll = RoleRemoveFromAllCommand(bot).terminal(base)
        val removeFromUser = RoleRemoveFromUserCommand(bot).terminal(base)

        return listOf(removeFromAll, removeFromUser)
    }
}
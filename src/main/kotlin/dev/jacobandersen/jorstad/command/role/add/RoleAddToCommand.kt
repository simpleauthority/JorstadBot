package dev.jacobandersen.jorstad.command.role.add

import cloud.commandframework.Command
import cloud.commandframework.arguments.StaticArgument
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.jorstad.JorstadBot
import dev.jacobandersen.jorstad.command.api.BaseSubcommand

class RoleAddToCommand(private val bot: JorstadBot) : BaseSubcommand {
    override fun children(builder: Command.Builder<JavacordCommandSender>): List<Command.Builder<JavacordCommandSender>> {
        val base = builder
            .argument(StaticArgument.of("add"))
            .argument(StaticArgument.of("to"))

        val addToAll = RoleAddToAllCommand(bot).terminal(base)
        val addToUser = RoleAddToUserCommand(bot).terminal(base)

        return listOf(addToAll, addToUser)
    }
}
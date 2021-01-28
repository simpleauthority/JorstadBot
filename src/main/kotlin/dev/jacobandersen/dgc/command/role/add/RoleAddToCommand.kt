package dev.jacobandersen.dgc.command.role.add

import cloud.commandframework.Command
import cloud.commandframework.arguments.StaticArgument
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.dgc.DgcBot
import dev.jacobandersen.dgc.command.api.BaseSubcommand

class RoleAddToCommand(private val bot: DgcBot) : BaseSubcommand {
    override fun children(builder: Command.Builder<JavacordCommandSender>): List<Command.Builder<JavacordCommandSender>> {
        val base = builder
            .argument(StaticArgument.of("add"))
            .argument(StaticArgument.of("to"))

        val addToAll = RoleAddToAllCommand(bot).terminal(base)
        val addToUser = RoleAddToUserCommand(bot).terminal(base)

        return listOf(addToAll, addToUser)
    }
}
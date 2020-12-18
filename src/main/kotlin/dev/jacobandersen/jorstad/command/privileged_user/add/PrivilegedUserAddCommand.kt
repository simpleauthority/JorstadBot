package dev.jacobandersen.jorstad.command.privileged_user.add

import cloud.commandframework.Command
import cloud.commandframework.arguments.StaticArgument
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.jorstad.JorstadBot
import dev.jacobandersen.jorstad.command.api.BaseSubcommand

class PrivilegedUserAddCommand(private val bot: JorstadBot) : BaseSubcommand {
    override fun children(builder: Command.Builder<JavacordCommandSender>): List<Command.Builder<JavacordCommandSender>> {
        val base = builder.argument(StaticArgument.of("add"))
        val privilegedUserAddUser = PrivilegedUserAddUserCommand(bot).terminal(base)
        val privilegedUserRemoveUser = PrivilegedUserAddPrivilegeCommand(bot).terminal(base)

        return listOf(privilegedUserAddUser, privilegedUserRemoveUser)
    }
}
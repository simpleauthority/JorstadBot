package dev.jacobandersen.dgc.command.privileged_user.remove

import cloud.commandframework.Command
import cloud.commandframework.arguments.StaticArgument
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.dgc.DgcBot
import dev.jacobandersen.dgc.command.api.BaseSubcommand

class PrivilegedUserRemoveCommand(private val bot: DgcBot) : BaseSubcommand {
    override fun children(builder: Command.Builder<JavacordCommandSender>): List<Command.Builder<JavacordCommandSender>> {
        val base = builder.argument(StaticArgument.of("remove"))
        val privilegedUserRemoveUser = PrivilegedUserRemoveUserCommand(bot).terminal(base)
        val privilegedUserRemovePrivilege = PrivilegedUserRemovePrivilegeCommand(bot).terminal(base)

        return listOf(privilegedUserRemoveUser, privilegedUserRemovePrivilege)
    }
}
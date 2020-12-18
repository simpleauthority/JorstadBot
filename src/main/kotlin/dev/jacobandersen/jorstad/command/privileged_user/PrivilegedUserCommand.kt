package dev.jacobandersen.jorstad.command.privileged_user

import cloud.commandframework.Command
import cloud.commandframework.javacord.JavacordCommandManager
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.jorstad.JorstadBot
import dev.jacobandersen.jorstad.command.api.BasicCommand
import dev.jacobandersen.jorstad.command.privileged_user.add.PrivilegedUserAddCommand
import dev.jacobandersen.jorstad.command.privileged_user.remove.PrivilegedUserRemoveCommand
import dev.jacobandersen.jorstad.data.privileged_users.PrivilegedUser

class PrivilegedUserCommand(private val bot: JorstadBot) : BasicCommand() {
    override fun construct(manager: JavacordCommandManager<JavacordCommandSender>): List<Command<JavacordCommandSender>> {
        val base = manager
            .commandBuilder("privilegeduser", "privuser", "puser")
            .permission(PrivilegedUser.Privilege.PRIVILEGED_USER_COMMAND.permission())

        val privilegedUserAdd = PrivilegedUserAddCommand(bot).children(base).map { it.build() }
        val privilegedUserRemove = PrivilegedUserRemoveCommand(bot).children(base).map { it.build() }

        val list = ArrayList<Command<JavacordCommandSender>>()
        list.addAll(privilegedUserAdd)
        list.addAll(privilegedUserRemove)

        return list
    }
}
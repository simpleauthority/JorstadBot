package dev.jacobandersen.dgc.command.role

import cloud.commandframework.Command
import cloud.commandframework.javacord.JavacordCommandManager
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.dgc.DgcBot
import dev.jacobandersen.dgc.command.api.BasicCommand
import dev.jacobandersen.dgc.command.role.add.RoleAddToCommand
import dev.jacobandersen.dgc.command.role.remove.RoleRemoveFromCommand
import dev.jacobandersen.dgc.data.privileged_users.PrivilegedUser

class RoleCommand(private val bot: DgcBot) : BasicCommand() {
    override fun construct(manager: JavacordCommandManager<JavacordCommandSender>): List<Command<JavacordCommandSender>> {
        val base = manager.commandBuilder("role").permission(PrivilegedUser.Privilege.ROLE_COMMAND.permission())

        val roleRemoveFrom = RoleRemoveFromCommand(bot).children(base).map { it.build() }
        val roleAddTo = RoleAddToCommand(bot).children(base).map { it.build() }

        val list = ArrayList<Command<JavacordCommandSender>>()
        list.addAll(roleRemoveFrom)
        list.addAll(roleAddTo)

        return list
    }
}
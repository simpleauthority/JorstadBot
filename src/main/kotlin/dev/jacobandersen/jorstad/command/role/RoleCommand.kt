package dev.jacobandersen.jorstad.command.role

import cloud.commandframework.Command
import cloud.commandframework.javacord.JavacordCommandManager
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.jorstad.JorstadBot
import dev.jacobandersen.jorstad.command.api.BasicCommand
import dev.jacobandersen.jorstad.command.role._default.RoleDefaultCommand
import dev.jacobandersen.jorstad.command.role.add.RoleAddToCommand
import dev.jacobandersen.jorstad.command.role.remove.RoleRemoveFromCommand
import dev.jacobandersen.jorstad.data.privileged_users.PrivilegedUser

class RoleCommand(private val bot: JorstadBot) : BasicCommand() {
    override fun construct(manager: JavacordCommandManager<JavacordCommandSender>): List<Command<JavacordCommandSender>> {
        val base = manager.commandBuilder("role").permission(PrivilegedUser.Privilege.ROLE_COMMAND.permission())
        val roleDefault = RoleDefaultCommand(bot).children(base).map { it.build() }
        val roleRemoveFrom = RoleRemoveFromCommand(bot).children(base).map { it.build() }
        val roleAddTo = RoleAddToCommand(bot).children(base).map { it.build() }

        val list = ArrayList<Command<JavacordCommandSender>>()
        list.addAll(roleDefault)
        list.addAll(roleRemoveFrom)
        list.addAll(roleAddTo)

        return list
    }
}
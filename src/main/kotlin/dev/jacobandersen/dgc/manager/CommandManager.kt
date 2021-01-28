package dev.jacobandersen.dgc.manager

import cloud.commandframework.Command
import cloud.commandframework.execution.CommandExecutionCoordinator
import cloud.commandframework.javacord.JavacordCommandManager
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.dgc.DgcBot
import dev.jacobandersen.dgc.command.*
import dev.jacobandersen.dgc.command.config.ConfigCommand
import dev.jacobandersen.dgc.command.factoid.FactoidAddCommand
import dev.jacobandersen.dgc.command.factoid.FactoidCommand
import dev.jacobandersen.dgc.command.factoid.FactoidDelCommand
import dev.jacobandersen.dgc.command.privileged_user.PrivilegedUserCommand
import dev.jacobandersen.dgc.command.role.RoleCommand
import dev.jacobandersen.dgc.data.privileged_users.PrivilegedUser
import dev.jacobandersen.dgc.ext.resolveGuildFromSender
import dev.jacobandersen.dgc.util.Log
import java.util.function.BiFunction
import java.util.function.Function

class CommandManager(private val bot: DgcBot) {
    private lateinit var manager: JavacordCommandManager<JavacordCommandSender>

    fun registerCommands(discord: DiscordManager) {
        initialize(discord)
        listOf(
            KillCommand(), LartCommand(), TacoCommand(),
            JorstadCommand(), FactoidCommand(bot), RoleCommand(bot),
            PrivilegedUserCommand(bot), ConfigCommand(bot)
        ).forEach { registerCommand(it.construct(manager)) }
    }

    private fun initialize(discord: DiscordManager) {
        Log.info("Initializing Command Manager...")
        manager = JavacordCommandManager(
            discord.api,
            CommandExecutionCoordinator.simpleCoordinator(),
            Function.identity(),
            Function.identity(),
            { "!" },
            BiFunction { sender, perm ->
                val guild = bot.discord.api.resolveGuildFromSender(sender) ?: return@BiFunction false
                val privilegedUser =
                    bot.data.privilegedUser.findPrivilegedUser(guild.id, sender.author.id) ?: return@BiFunction false
                return@BiFunction PrivilegedUser.Privilege.fromString(perm)?.hasPermission(privilegedUser) ?: false
            }
        )
    }

    private fun registerCommand(command: List<Command<JavacordCommandSender>>) {
        command.forEach { manager.command(it) }
    }
}
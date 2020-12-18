package dev.jacobandersen.jorstad.manager

import cloud.commandframework.Command
import cloud.commandframework.execution.CommandExecutionCoordinator
import cloud.commandframework.javacord.JavacordCommandManager
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.jorstad.JorstadBot
import dev.jacobandersen.jorstad.command.*
import dev.jacobandersen.jorstad.command.config.ConfigCommand
import dev.jacobandersen.jorstad.command.privileged_user.PrivilegedUserCommand
import dev.jacobandersen.jorstad.command.role.RoleCommand
import dev.jacobandersen.jorstad.data.privileged_users.PrivilegedUser
import dev.jacobandersen.jorstad.ext.resolveGuildFromSender
import dev.jacobandersen.jorstad.util.Log
import java.util.function.BiFunction
import java.util.function.Function

class CommandManager(private val bot: JorstadBot) {
    private lateinit var manager: JavacordCommandManager<JavacordCommandSender>

    fun registerCommands(discord: DiscordManager) {
        initialize(discord)
        listOf(
            KillCommand(), LartCommand(), TacoCommand(),
            RememberCommand(bot), ForgetCommand(bot),
            RoleCommand(bot), PrivilegedUserCommand(bot),
            ConfigCommand(bot)
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
                val privilegedUser = bot.data.privilegedUser.findPrivilegedUser(guild.id, sender.author.id) ?: return@BiFunction false
                return@BiFunction PrivilegedUser.Privilege.fromString(perm).hasPermission(privilegedUser)
            }
        )
    }

    private fun registerCommand(command: List<Command<JavacordCommandSender>>) {
        command.forEach { manager.command(it) }
    }
}
package dev.jacobandersen.jorstad.manager

import cloud.commandframework.Command
import cloud.commandframework.execution.CommandExecutionCoordinator
import cloud.commandframework.javacord.JavacordCommandManager
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.jorstad.JorstadBot
import dev.jacobandersen.jorstad.command.*
import dev.jacobandersen.jorstad.command.role.RoleCommand
import dev.jacobandersen.jorstad.util.Log
import java.util.function.Function

class CommandManager(private val bot: JorstadBot) {
    private lateinit var manager: JavacordCommandManager<JavacordCommandSender>

    fun registerCommands(discord: DiscordManager) {
        initialize(discord)
        registerCommand(KillCommand().construct(manager))
        registerCommand(LartCommand().construct(manager))
        registerCommand(TacoCommand().construct(manager))
        registerCommand(RememberCommand(bot).construct(manager))
        registerCommand(ForgetCommand(bot).construct(manager))
        registerCommand(RoleCommand(bot).construct(manager))
    }

    private fun initialize(discord: DiscordManager) {
        Log.info("Initializing Command Manager...")
        manager = JavacordCommandManager(
            discord.api,
            CommandExecutionCoordinator.simpleCoordinator(),
            Function.identity(),
            Function.identity(),
            { "!" },
            null
        )
    }

    private fun registerCommand(command: List<Command<JavacordCommandSender>>) {
        command.forEach { manager.command(it) }
    }
}
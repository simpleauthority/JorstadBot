package dev.jacobandersen.jorstad.manager

import cloud.commandframework.Command
import cloud.commandframework.execution.CommandExecutionCoordinator
import cloud.commandframework.javacord.JavacordCommandManager
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.jorstad.command.KillCommand
import dev.jacobandersen.jorstad.command.LartCommand
import dev.jacobandersen.jorstad.util.Log
import java.util.function.Function

class CommandManager() {
    private lateinit var manager: JavacordCommandManager<JavacordCommandSender>

    fun registerCommands(discord: DiscordManager) {
        initialize(discord)
        registerCommand(KillCommand(manager).build())
        registerCommand(LartCommand(manager).build())
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

    private fun registerCommand(command: Command<JavacordCommandSender>) {
        Log.info("Registering Command: " + command.arguments[0].name)
        manager.command(command)
    }
}
package dev.jacobandersen.jorstad.manager

import cloud.commandframework.Command
import cloud.commandframework.execution.CommandExecutionCoordinator
import cloud.commandframework.javacord.JavacordCommandManager
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.jorstad.JorstadBot
import dev.jacobandersen.jorstad.command.*
import dev.jacobandersen.jorstad.util.Log
import java.util.function.Function

class CommandManager(private val bot: JorstadBot) {
    private lateinit var manager: JavacordCommandManager<JavacordCommandSender>

    fun registerCommands(discord: DiscordManager) {
        initialize(discord)
        registerCommand(KillCommand(manager).build())
        registerCommand(LartCommand(manager).build())
        registerCommand(TacoCommand(manager).build())
        registerCommand(RememberCommand(bot, manager).build())
        registerCommand(ForgetCommand(bot, manager).build())
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

    fun registerCommand(command: Command<JavacordCommandSender>) {
        Log.info("Registering Command: ${command.arguments[0].name}")
        manager.command(command)
    }

    fun unregisterCommand(name: String) {
        Log.info("Unregistering Command: $name")
        Log.warn("Command not actually unregistered because Cloud has no unregistration yet.")
    }
}
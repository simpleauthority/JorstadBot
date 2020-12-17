package dev.jacobandersen.jorstad

import dev.jacobandersen.jorstad.manager.DataManager
import dev.jacobandersen.jorstad.manager.CommandManager
import dev.jacobandersen.jorstad.manager.ConfigManager
import dev.jacobandersen.jorstad.manager.DiscordManager
import dev.jacobandersen.jorstad.util.Log

fun main() {
    JorstadBot().run()
}

class JorstadBot {
    val discord = DiscordManager(this)
    val command = CommandManager(this)
    val config = ConfigManager()
    val data = DataManager()

    fun run() {
        Log.info("Booting JorstadBot...")
        Log.info("Registering shutdown hook...")
        Runtime.getRuntime().addShutdownHook(Thread { discord.logout() })
        Log.info("Registered shutdown hook.")
        discord.login()
        Log.info("JorstadBot invite link is: ${discord.getInvite()}")
        command.registerCommands(discord)
        Log.info("JorstadBot is now running.")
    }
}
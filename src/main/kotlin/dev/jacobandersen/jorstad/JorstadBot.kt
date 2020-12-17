package dev.jacobandersen.jorstad

import dev.jacobandersen.jorstad.manager.DataManager
import dev.jacobandersen.jorstad.manager.CommandManager
import dev.jacobandersen.jorstad.manager.DiscordManager
import dev.jacobandersen.jorstad.util.Log

fun main() {
    JorstadBot().run()
}

class JorstadBot {
    val discord = DiscordManager()
    val command = CommandManager(this)
    val data = DataManager()

    fun run() {
        Log.info("Booting JorstadBot...")
        enableShutdownHook()
        discord.login()
        Log.info("JorstadBot invite link is: ${discord.getInvite()}")
        command.registerCommands(discord)
        Log.info("JorstadBot is now running.")
    }

    private fun enableShutdownHook() {
        Log.info("Registering shutdown hook...")
        Runtime.getRuntime().addShutdownHook(Thread { shutdown() })
        Log.info("Registered shutdown hook.")
    }

    private fun shutdown() {
        discord.logout()
    }
}
package dev.jacobandersen.jorstad

import dev.jacobandersen.jorstad.data.privileged_users.PrivilegedUser
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
        handleShutdown()
        handleLogin()
        registerCommands()
        checkGuildConfigs()
        checkGuildOwnersPrivileged()
        Log.info("JorstadBot is now running.")
    }

    private fun handleShutdown() {
        Log.info("Registering shutdown hook...")
        Runtime.getRuntime().addShutdownHook(Thread { discord.logout() })
    }

    private fun handleLogin() {
        discord.login()
        Log.info("JorstadBot invite link is: ${discord.getInvite()}")
    }

    private fun registerCommands() {
        Log.info("Registering commands...")
        command.registerCommands(discord)
    }

    private fun checkGuildConfigs() {
        Log.info("Checking guild configs...")
        discord.api.servers.forEach { server ->
            val id = server.id

            if (!config.hasGuildConfig(id)) {
                Log.info("Guild $id has no associated configuration. Creating now...")
                config.createGuildConfig(id)
            }
        }
    }

    private fun checkGuildOwnersPrivileged() {
        Log.info("Checking that guild owners are privileged...")
        discord.api.servers.forEach { server ->
            val db = data.privilegedUser
            if (!db.privilegedUserExists(server.id, server.ownerId)) {
                db.addPrivilegedUser(server.id, server.ownerId, listOf(PrivilegedUser.Privilege.ALL))
            }
        }
    }
}
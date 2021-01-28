package dev.jacobandersen.dgc

import dev.jacobandersen.dgc.data.privileged_users.PrivilegedUser
import dev.jacobandersen.dgc.manager.CommandManager
import dev.jacobandersen.dgc.manager.ConfigManager
import dev.jacobandersen.dgc.manager.DataManager
import dev.jacobandersen.dgc.manager.DiscordManager
import dev.jacobandersen.dgc.util.Log

class DgcBot {
    val discord = DiscordManager(this)
    val command = CommandManager(this)
    val config = ConfigManager()
    val data = DataManager()

    fun run() {
        Log.info("Booting Dedicated Guidance Cyborg...")
        handleLogin()
        registerCommands()
        checkGuildConfigs()
        checkGuildOwnersPrivileged()
        Log.info("Dedicated Guidance Cyborg is now running.")
    }

    fun stop() {
        discord.logout()
        data.stop()
    }

    private fun handleLogin() {
        discord.login()
        Log.info("Invite link is: ${discord.getInvite()}")
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
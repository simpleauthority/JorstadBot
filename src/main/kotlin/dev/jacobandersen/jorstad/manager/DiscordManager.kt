package dev.jacobandersen.jorstad.manager

import dev.jacobandersen.jorstad.JorstadBot
import dev.jacobandersen.jorstad.listeners.*
import dev.jacobandersen.jorstad.util.Log
import org.javacord.api.DiscordApi
import org.javacord.api.DiscordApiBuilder
import org.javacord.api.entity.permission.Permissions

class DiscordManager(private val bot: JorstadBot) {
    private val builder = DiscordApiBuilder()
    lateinit var api: DiscordApi

    fun login() {
        Log.info("Logging into Discord...")
        api = builder
            .setToken(System.getenv("JORSTAD_BOT_TOKEN"))
            .setAllIntents()
            .addMessageCreateListener(MessageListener(bot))
            .addServerJoinListener(BotJoinGuildListener(bot))
            .addServerLeaveListener(BotLeaveGuildListener(bot))
            .addServerMemberJoinListener(UserJoinGuildListener(bot))
            .addServerMemberLeaveListener(UserLeaveGuildListener(bot))
            .login()
            .join()
        Log.info("Successfully connected to Discord.")
    }

    fun logout() {
        Log.info("Logging out of Discord...")
        api.disconnect()
        Log.info("Successfully logged out of Discord.")
    }

    fun getInvite(): String {
        return api.createBotInvite(Permissions.fromBitmask(8))
    }

}

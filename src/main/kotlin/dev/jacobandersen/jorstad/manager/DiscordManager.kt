package dev.jacobandersen.jorstad.manager

import dev.jacobandersen.jorstad.JorstadBot
import dev.jacobandersen.jorstad.listeners.BotJoinGuildListener
import dev.jacobandersen.jorstad.listeners.BotLeaveGuildListener
import dev.jacobandersen.jorstad.listeners.MessageListener
import dev.jacobandersen.jorstad.listeners.UserJoinGuildListener
import dev.jacobandersen.jorstad.util.Log
import org.javacord.api.DiscordApi
import org.javacord.api.DiscordApiBuilder
import org.javacord.api.entity.permission.Permissions
import org.javacord.api.listener.message.MessageCreateListener
import org.javacord.api.listener.server.ServerJoinListener
import org.javacord.api.listener.server.ServerLeaveListener
import org.javacord.api.listener.server.member.ServerMemberJoinListener

class DiscordManager(private val bot: JorstadBot) {
    private val builder = DiscordApiBuilder()
    lateinit var api: DiscordApi

    fun login() {
        Log.info("Logging into Discord...")
        api = builder
            .setToken(System.getenv("JORSTAD_BOT_TOKEN"))
            .setAllIntents()
            .addListener(MessageCreateListener::class.java, MessageListener(bot))
            .addListener(ServerJoinListener::class.java, BotJoinGuildListener(bot))
            .addListener(ServerLeaveListener::class.java, BotLeaveGuildListener(bot))
            .addListener(ServerMemberJoinListener::class.java, UserJoinGuildListener(bot))
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

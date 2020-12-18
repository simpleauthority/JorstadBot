package dev.jacobandersen.jorstad.listeners

import dev.jacobandersen.jorstad.JorstadBot
import org.javacord.api.event.server.ServerLeaveEvent
import org.javacord.api.listener.server.ServerLeaveListener

class BotLeaveGuildListener(private val bot: JorstadBot) : ServerLeaveListener {
    override fun onServerLeave(event: ServerLeaveEvent) {
        val guildId = event.server.id
        bot.config.deleteGuildConfig(guildId)
        bot.data.privilegedUser.deletePrivilegedUsers(guildId)
    }
}
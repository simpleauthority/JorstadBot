package dev.jacobandersen.dgc.listeners

import dev.jacobandersen.dgc.DgcBot
import org.javacord.api.event.server.ServerLeaveEvent
import org.javacord.api.listener.server.ServerLeaveListener

class BotLeaveGuildListener(private val bot: DgcBot) : ServerLeaveListener {
    override fun onServerLeave(event: ServerLeaveEvent) {
        val guildId = event.server.id
        bot.config.deleteGuildConfig(guildId)
        bot.data.privilegedUser.deletePrivilegedUsers(guildId)
    }
}
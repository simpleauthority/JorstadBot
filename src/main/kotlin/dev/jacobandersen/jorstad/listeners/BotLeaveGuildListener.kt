package dev.jacobandersen.jorstad.listeners

import dev.jacobandersen.jorstad.JorstadBot
import org.javacord.api.event.server.ServerLeaveEvent
import org.javacord.api.listener.server.ServerLeaveListener

class BotLeaveGuildListener(private val bot: JorstadBot) : ServerLeaveListener {
    override fun onServerLeave(event: ServerLeaveEvent) {
        bot.config.deleteGuild(event.server.id)
    }
}
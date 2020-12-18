package dev.jacobandersen.jorstad.listeners

import dev.jacobandersen.jorstad.JorstadBot
import org.javacord.api.event.server.member.ServerMemberLeaveEvent
import org.javacord.api.listener.server.member.ServerMemberLeaveListener

class UserLeaveGuildListener(private val bot: JorstadBot) : ServerMemberLeaveListener {
    override fun onServerMemberLeave(event: ServerMemberLeaveEvent) {
        val config = bot.config.readGuildConfig(event.server.id) ?: return

        val leaveMessage = config.leaveMessage ?: ""
        if (leaveMessage.isNotEmpty()) {
            val channel = event.server.systemChannel.orElse(null) ?: return
            channel.sendMessage(leaveMessage.replace("{user}", event.user.mentionTag))
        }
    }
}
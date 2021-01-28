package dev.jacobandersen.dgc.listeners

import dev.jacobandersen.dgc.DgcBot
import org.javacord.api.event.server.member.ServerMemberLeaveEvent
import org.javacord.api.listener.server.member.ServerMemberLeaveListener

class UserLeaveGuildListener(private val bot: DgcBot) : ServerMemberLeaveListener {
    override fun onServerMemberLeave(event: ServerMemberLeaveEvent) {
        val config = bot.config.readGuildConfig(event.server.id) ?: return

        val leaveMessage = config.leaveMessage ?: ""
        if (leaveMessage.isNotEmpty()) {
            val channel = event.server.systemChannel.orElse(null) ?: return
            channel.sendMessage(leaveMessage.replace("{user}", event.user.mentionTag))
        }
    }
}
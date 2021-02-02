package dev.jacobandersen.dgc.listeners

import dev.jacobandersen.dgc.DgcBot
import org.javacord.api.event.message.MessageDeleteEvent
import org.javacord.api.listener.message.MessageDeleteListener

class MessageDeleteListener(private val bot: DgcBot) : MessageDeleteListener {
    override fun onMessageDelete(event: MessageDeleteEvent) {
        val author = event.messageAuthor.orElse(null) ?: return
        val user = author.asUser().orElse(null) ?: return

        bot.data.activityTracker.updateByUserId(user.id) { it.track(event) }
    }
}
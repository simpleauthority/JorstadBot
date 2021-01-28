package dev.jacobandersen.dgc.listeners

import dev.jacobandersen.dgc.DgcBot
import org.javacord.api.event.message.MessageCreateEvent
import org.javacord.api.listener.message.MessageCreateListener

class MessageListener(private val bot: DgcBot) : MessageCreateListener {
    override fun onMessageCreate(event: MessageCreateEvent) {
        val message = event.message.content
        val guildId = event.server.orElse(null)?.id ?: return

        if (message.startsWith("!")) {
            val rawCommand = message.subSequence(1, message.length).split(" ")[0].toLowerCase()
            val command = bot.data.textCommand.findTextCommand(guildId, rawCommand)

            if (command != null) {
                event.channel.sendMessage(command.output)
            }
        }
    }
}
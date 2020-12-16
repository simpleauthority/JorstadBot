package dev.jacobandersen.jorstad.command

import cloud.commandframework.Command
import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.javacord.JavacordCommandManager
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.jorstad.module.KillModule
import org.javacord.api.entity.message.MessageBuilder
import org.javacord.api.entity.message.MessageDecoration

class KillCommand(manager: JavacordCommandManager<JavacordCommandSender>) : BasicCommand(manager) {
    private val module = KillModule()

    override fun construct(manager: JavacordCommandManager<JavacordCommandSender>): Command<JavacordCommandSender> {
        return manager.commandBuilder("kill")
            .argument(StringArgument.single("name"))
            .handler {
                MessageBuilder()
                    .append(module.generateTargetedAtUser(it["name"]), MessageDecoration.ITALICS)
                    .send(it.sender.textChannel)
            }
            .build()
    }
}
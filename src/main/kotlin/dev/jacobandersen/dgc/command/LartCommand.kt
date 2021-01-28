package dev.jacobandersen.dgc.command

import cloud.commandframework.Command
import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.javacord.JavacordCommandManager
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.dgc.command.api.BasicCommand
import dev.jacobandersen.dgc.module.LartModule
import org.javacord.api.entity.message.MessageBuilder
import org.javacord.api.entity.message.MessageDecoration

class LartCommand : BasicCommand() {
    private val module = LartModule()

    override fun construct(manager: JavacordCommandManager<JavacordCommandSender>): List<Command<JavacordCommandSender>> {
        return listOf(manager.commandBuilder("lart")
            .argument(StringArgument.single("name"))
            .handler {
                MessageBuilder()
                    .append(module.generateTargetedAtUser(it["name"]), MessageDecoration.ITALICS)
                    .send(it.sender.textChannel)
            }
            .build())
    }
}
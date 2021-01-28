package dev.jacobandersen.dgc.command

import cloud.commandframework.Command
import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.javacord.JavacordCommandManager
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.dgc.command.api.BasicCommand
import dev.jacobandersen.dgc.module.JorstadModule
import dev.jacobandersen.dgc.module.KillModule
import org.javacord.api.entity.message.MessageBuilder
import org.javacord.api.entity.message.MessageDecoration

class JorstadCommand : BasicCommand() {
    private val module = JorstadModule()

    override fun construct(manager: JavacordCommandManager<JavacordCommandSender>): List<Command<JavacordCommandSender>> {
        return listOf(manager.commandBuilder("jorstad")
            .argument(StringArgument.single("name"))
            .handler {
                MessageBuilder()
                    .append(module.generateTargetedAtUser(it["name"]), MessageDecoration.ITALICS)
                    .send(it.sender.textChannel)
            }
            .build())
    }
}
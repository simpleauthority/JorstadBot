package dev.jacobandersen.jorstad.command

import cloud.commandframework.Command
import cloud.commandframework.javacord.JavacordCommandManager
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.jorstad.data.text_commands.TextCommand

class TextCommandCommand(private val command: TextCommand, manager: JavacordCommandManager<JavacordCommandSender>) :
    BasicCommand(manager) {
    override fun construct(manager: JavacordCommandManager<JavacordCommandSender>): Command<JavacordCommandSender> {
        return manager.commandBuilder(command.name)
            .handler {
                it.sender.sendMessage(command.output)
            }
            .build()
    }
}
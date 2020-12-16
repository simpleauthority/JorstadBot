package dev.jacobandersen.jorstad.command

import cloud.commandframework.Command
import cloud.commandframework.javacord.JavacordCommandManager
import cloud.commandframework.javacord.sender.JavacordCommandSender

abstract class BasicCommand(private val manager: JavacordCommandManager<JavacordCommandSender>) {
    fun build(): Command<JavacordCommandSender> {
        return construct(manager)
    }

    abstract fun construct(manager: JavacordCommandManager<JavacordCommandSender>): Command<JavacordCommandSender>
}
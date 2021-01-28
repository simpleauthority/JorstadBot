package dev.jacobandersen.dgc.command.api

import cloud.commandframework.Command
import cloud.commandframework.javacord.JavacordCommandManager
import cloud.commandframework.javacord.sender.JavacordCommandSender

abstract class BasicCommand {
    abstract fun construct(manager: JavacordCommandManager<JavacordCommandSender>): List<Command<JavacordCommandSender>>
}
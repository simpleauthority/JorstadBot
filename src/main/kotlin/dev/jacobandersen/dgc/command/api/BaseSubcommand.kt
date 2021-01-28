package dev.jacobandersen.dgc.command.api

import cloud.commandframework.Command
import cloud.commandframework.javacord.sender.JavacordCommandSender

interface BaseSubcommand {
    fun children(builder: Command.Builder<JavacordCommandSender>): List<Command.Builder<JavacordCommandSender>>
}
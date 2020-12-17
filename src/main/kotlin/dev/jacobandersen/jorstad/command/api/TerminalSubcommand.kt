package dev.jacobandersen.jorstad.command.api

import cloud.commandframework.Command
import cloud.commandframework.javacord.sender.JavacordCommandSender

interface TerminalSubcommand {
    fun terminal(builder: Command.Builder<JavacordCommandSender>): Command.Builder<JavacordCommandSender>
}
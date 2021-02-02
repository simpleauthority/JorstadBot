package dev.jacobandersen.dgc.command.factoid

import cloud.commandframework.Command
import cloud.commandframework.arguments.StaticArgument
import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.dgc.DgcBot
import dev.jacobandersen.dgc.command.api.TerminalSubcommand

class FactoidDelCommand(private val bot: DgcBot) : TerminalSubcommand {
    override fun terminal(builder: Command.Builder<JavacordCommandSender>): Command.Builder<JavacordCommandSender> {
        return builder
            .argument(StaticArgument.of("del"))
            .argument(StringArgument.single("name"))
            .handler {
                val guildId = it.sender.event.server.orElse(null)?.id ?: return@handler
                val name = it.get<String>("name")

                if (!bot.data.textCommand.textCommandExists(guildId, name)) {
                    it.sender.sendErrorMessage("A text command with that name does not exist. :confused:")
                    return@handler
                }

                bot.data.textCommand.deleteTextCommand(guildId, name)
                it.sender.sendSuccessMessage("I have forgotten what to say when somebody types `!${name}`!")
            }
    }
}
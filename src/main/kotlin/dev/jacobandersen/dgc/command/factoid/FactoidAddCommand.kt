package dev.jacobandersen.dgc.command.factoid

import cloud.commandframework.Command
import cloud.commandframework.arguments.StaticArgument
import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.dgc.DgcBot
import dev.jacobandersen.dgc.command.api.TerminalSubcommand

class FactoidAddCommand(private val bot: DgcBot) : TerminalSubcommand {
    override fun terminal(builder: Command.Builder<JavacordCommandSender>): Command.Builder<JavacordCommandSender> {
        return builder
            .argument(StaticArgument.of("add"))
            .argument(StringArgument.single("name"))
            .argument(StringArgument.greedy("output"))
            .handler { it ->
                val guildId = it.sender.event.server.orElse(null)?.id ?: return@handler
                val name = it.get<String>("name")
                val output = it.get<String>("output")

                if (bot.data.textCommand.textCommandExists(guildId, name)) {
                    it.sender.sendErrorMessage("A text command with that name already exists. Choose another one or delete the already existing command.")
                    return@handler
                }

                bot.data.textCommand.addTextCommand(guildId, name, output)
                it.sender.sendSuccessMessage("I have remembered what to say when somebody types `!${name}`!")
            }
    }
}
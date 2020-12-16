package dev.jacobandersen.jorstad.command

import cloud.commandframework.Command
import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.javacord.JavacordCommandManager
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.jorstad.JorstadBot

class RememberCommand(private val bot: JorstadBot, manager: JavacordCommandManager<JavacordCommandSender>) :
    BasicCommand(manager) {
    override fun construct(manager: JavacordCommandManager<JavacordCommandSender>): Command<JavacordCommandSender> {
        return manager.commandBuilder("remember")
            .argument(StringArgument.single("name"))
            .argument(StringArgument.greedy("output"))
            .handler {
                val name = it.get<String>("name")
                val output = it.get<String>("output")

                if (bot.data.textCommand.nameExists(name)) {
                    it.sender.sendErrorMessage("A text command with that name already exists. Choose another one or delete the already existing command.")
                    return@handler
                }

                val command = bot.data.textCommand.addTextCommand(name, output)
                bot.command.registerCommand(TextCommandCommand(command, manager).build())
                it.sender.sendSuccessMessage("I have remembered what to say when somebody types `!${name}`!")
            }
            .build()
    }
}
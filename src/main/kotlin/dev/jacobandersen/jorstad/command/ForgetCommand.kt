package dev.jacobandersen.jorstad.command

import cloud.commandframework.Command
import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.javacord.JavacordCommandManager
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.jorstad.JorstadBot

class ForgetCommand(private val bot: JorstadBot, manager: JavacordCommandManager<JavacordCommandSender>) :
    BasicCommand(manager) {
    override fun construct(manager: JavacordCommandManager<JavacordCommandSender>): Command<JavacordCommandSender> {
        return manager.commandBuilder("forget")
            .argument(StringArgument.single("name"))
            .handler {
                val name = it.get<String>("name")

                if (!bot.data.textCommand.nameExists(name)) {
                    it.sender.sendErrorMessage("A text command with that name does not exist. :confused:")
                    return@handler
                }

                bot.data.textCommand.deleteTextCommand(name)
                bot.command.unregisterCommand(name)
                it.sender.sendSuccessMessage("I have forgotten what to say when somebody types `!${name}`! This will not take effect until next reboot.")
            }
            .build()
    }
}
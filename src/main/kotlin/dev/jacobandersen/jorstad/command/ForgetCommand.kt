package dev.jacobandersen.jorstad.command

import cloud.commandframework.Command
import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.javacord.JavacordCommandManager
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.jorstad.JorstadBot
import dev.jacobandersen.jorstad.command.api.BasicCommand
import dev.jacobandersen.jorstad.data.privileged_users.PrivilegedUser

class ForgetCommand(private val bot: JorstadBot) : BasicCommand() {
    override fun construct(manager: JavacordCommandManager<JavacordCommandSender>): List<Command<JavacordCommandSender>> {
        return listOf(manager.commandBuilder("forget")
            .permission(PrivilegedUser.Privilege.REMEMBER_FORGET_COMMAND.permission())
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
            .build())
    }
}
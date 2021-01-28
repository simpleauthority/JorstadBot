package dev.jacobandersen.dgc.command

import cloud.commandframework.Command
import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.javacord.JavacordCommandManager
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.dgc.DgcBot
import dev.jacobandersen.dgc.command.api.BasicCommand
import dev.jacobandersen.dgc.data.privileged_users.PrivilegedUser

class RememberCommand(private val bot: DgcBot) : BasicCommand() {
    override fun construct(manager: JavacordCommandManager<JavacordCommandSender>): List<Command<JavacordCommandSender>> {
        return listOf(manager.commandBuilder("remember")
            .permission(PrivilegedUser.Privilege.REMEMBER_FORGET_COMMAND.permission())
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
            .build())
    }
}
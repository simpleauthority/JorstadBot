package dev.jacobandersen.dgc.command.api.argument

import cloud.commandframework.arguments.parser.ArgumentParseResult
import cloud.commandframework.arguments.parser.ArgumentParser
import cloud.commandframework.context.CommandContext
import cloud.commandframework.exceptions.parsing.NoInputProvidedException
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.dgc.DgcBot
import dev.jacobandersen.dgc.util.Log
import org.javacord.api.entity.user.User
import java.util.*

class UserParser(private val bot: DgcBot) : ArgumentParser<JavacordCommandSender, User> {
    override fun parse(
        commandContext: CommandContext<JavacordCommandSender>,
        inputQueue: Queue<String>
    ): ArgumentParseResult<User> {
        val input = inputQueue.peek()
            ?: return ArgumentParseResult.failure(
                NoInputProvidedException(
                    UserParser::class.java,
                    commandContext
                )
            )
        inputQueue.remove()

        Log.info("Got input: $input")

        val idRegex: MatchResult = Regex("<@!(\\d+)>").matchEntire(input) ?: return invalidUser()
        Log.info("Got regex ${idRegex.groupValues}")

        val id = (idRegex.groups[1] ?: return invalidUser()).value.toLongOrNull() ?: return invalidUser()
        Log.info("Got id $id")

        val user = bot.discord.api.getCachedUserById(id).orElse(null) ?: return user404()
        Log.info("Got user $user")

        return ArgumentParseResult.success(user)
    }

    override fun suggestions(
        commandContext: CommandContext<JavacordCommandSender>,
        input: String
    ): MutableList<String> {
        return super.suggestions(commandContext, input)
    }

    private fun user404(): ArgumentParseResult<User> {
        Log.info("Exiting! 404.")
        return ArgumentParseResult.failure(RuntimeException("That user could not be found. (Are they even in this guild?)"))
    }

    private fun invalidUser(): ArgumentParseResult<User> {
        Log.info("Exiting! Invalid.")
        return ArgumentParseResult.failure(IllegalArgumentException("That does not look like a valid user!"))
    }
}
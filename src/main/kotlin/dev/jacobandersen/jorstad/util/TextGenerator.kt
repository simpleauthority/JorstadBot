package dev.jacobandersen.jorstad.util

import java.util.concurrent.ThreadLocalRandom

object TextGenerator {
    private val random = ThreadLocalRandom.current()

    fun generate(templates: List<String>, components: Map<String, List<String>>, variables: Map<String, String>): String {
        var message = chooseRandom(templates)

        components.forEach { (key, value) ->
            message = message.replace("{${key}}", chooseRandom(value))
        }

        variables.forEach { (key, value) -> message = message.replace("{${key}}", value) }

        return message
    }

    private fun chooseRandom(choices: List<String>): String {
        return choices[random.nextInt(choices.size - 1)]
    }
}
package dev.jacobandersen.dgc.task.tasks

import dev.jacobandersen.dgc.manager.DiscordManager
import dev.jacobandersen.dgc.task.Task

class InactiveUserPruneTask(private val discord: DiscordManager) : Task("Inactive User Prune", "30 * * * *") {
    override fun task() {
        val api = discord.api

        // check shit
    }
}
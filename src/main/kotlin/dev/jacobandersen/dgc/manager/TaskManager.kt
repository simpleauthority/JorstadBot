package dev.jacobandersen.dgc.manager

import dev.jacobandersen.dgc.DgcBot
import dev.jacobandersen.dgc.task.tasks.InactiveUserPruneTask
import dev.jacobandersen.dgc.util.Log
import it.sauronsoftware.cron4j.Scheduler
import java.util.*

class TaskManager(private val bot: DgcBot) {
    private val scheduler: Scheduler

    init {
        val scheduler = Scheduler()
        scheduler.timeZone = TimeZone.getTimeZone("America/Los_Angeles")
        this.scheduler = scheduler
    }

    fun registerTasks() {
        val tasks = listOf(
            InactiveUserPruneTask(bot.discord)
        )

        tasks.forEach { scheduler.schedule(it.pattern, it.getTask()) }
    }

    fun start() {
        Log.info("Starting scheduled task manager...")
        scheduler.start()
    }

    fun stop() {
        Log.info("Stopping scheduled task manager...")
        scheduler.stop()
    }
}
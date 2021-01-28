package dev.jacobandersen.dgc.task

import dev.jacobandersen.dgc.util.Log

abstract class Task(val name: String, val pattern: String) {
    abstract fun task()

    fun getTask(): Runnable {
        return Runnable {
            Log.info("Running scheduled task: $name")
            task()
        }
    }
}
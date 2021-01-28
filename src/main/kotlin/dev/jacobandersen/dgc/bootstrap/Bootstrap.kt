package dev.jacobandersen.dgc.bootstrap

import dev.jacobandersen.dgc.DgcBot

fun main() {
    val dgc = DgcBot()

    Runtime.getRuntime().addShutdownHook(Thread { dgc.stop() })

    dgc.run()
}
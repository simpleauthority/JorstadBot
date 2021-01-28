package dev.jacobandersen.dgc.util

import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.apache.logging.log4j.core.config.Configurator

object Log {
    private val logger: Logger

    init {
        Configurator.setRootLevel(Level.INFO)
        logger = LogManager.getLogger("DgcLogger")
    }

    fun info(msg: String) = logger.info(msg)
    fun warn(msg: String) = logger.warn(msg)
    fun err(msg: String) = logger.error(msg)
}
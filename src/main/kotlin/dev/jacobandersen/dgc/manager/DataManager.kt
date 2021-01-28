package dev.jacobandersen.dgc.manager

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dev.jacobandersen.dgc.data.privileged_users.PrivilegedUserDam
import dev.jacobandersen.dgc.data.text_commands.TextCommandDam
import dev.jacobandersen.dgc.util.Log
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin

class DataManager {
    private val hikari: HikariDataSource
    val jdbi: Jdbi
    val textCommand: TextCommandDam
    val privilegedUser: PrivilegedUserDam

    init {
        Log.info("Configuring data storage system...")
        val config = HikariConfig()
        config.poolName = "Dedicated Guidance Cyborg SQLite Pool"
        config.jdbcUrl = "jdbc:sqlite:data/dgc.db"

        Log.info("Connecting to data storage...")
        hikari = HikariDataSource(config)
        jdbi = Jdbi.create(hikari)
        jdbi.installPlugin(SqlObjectPlugin())
        jdbi.installPlugin(KotlinSqlObjectPlugin())

        Log.info("Creating data accessors...")
        textCommand = TextCommandDam(this)
        privilegedUser = PrivilegedUserDam(this)

        Log.info("Creating data containers (if necessary)...")
        textCommand.createContainer()
        privilegedUser.createContainer()
    }

    fun stop() {
        Log.info("Shutting down data storage...")
        hikari.close()
    }
}
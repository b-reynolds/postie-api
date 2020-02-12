package database

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin
import utilities.EnvHelper

private const val ENV_DATABASE_HOST = "DATABASE_HOST"
private const val ENV_DATABASE_PORT = "DATABASE_PORT"
private const val ENV_DATABASE_USER = "DATABASE_USER"

/**
 * Database
 */
object Database {
    private val host = EnvHelper.get(ENV_DATABASE_HOST)
    private val port = EnvHelper.getInt(ENV_DATABASE_PORT)
    private val user = EnvHelper.get(ENV_DATABASE_USER)

    /**
     * Connects to and returns the data source.
     */
    fun connect(): Jdbi {
        return Jdbi.create("$host:$port/?user=$user").apply {
            installPlugin(KotlinSqlObjectPlugin())
            installPlugin(KotlinPlugin())
        }
    }
}

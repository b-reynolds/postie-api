package satchel

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin
import utilities.EnvHelper

private const val ENV_SATCHEL_HOST = "SATCHEL_HOST"
private const val ENV_SATCHEL_PORT = "SATCHEL_PORT"
private const val ENV_SATCHEL_USER = "SATCHEL_USER"

/**
 * Satchel
 */
object Satchel {
    private val host = EnvHelper.get(ENV_SATCHEL_HOST)
    private val port = EnvHelper.getInt(ENV_SATCHEL_PORT)
    private val user = EnvHelper.get(ENV_SATCHEL_USER)

    /**
     * Connects to and returns the Satchel data source.
     */
    fun connect(): Jdbi {
        return Jdbi.create("$host:$port/?user=$user").apply {
            installPlugin(KotlinSqlObjectPlugin())
            installPlugin(KotlinPlugin())
        }
    }
}
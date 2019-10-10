package satchel

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin

private const val DB_HOST = "jdbc:postgresql://localhost"
private const val DB_PORT = 5432
private const val DB_USER = "postgres"

/**
 * Satchel
 */
object Satchel {
    /**
     * Connects to and returns the Satchel data source.
     */
    fun connect(): Jdbi {
        return Jdbi.create("$DB_HOST:$DB_PORT/?user=$DB_USER").apply {
            installPlugin(KotlinSqlObjectPlugin())
            installPlugin(KotlinPlugin())
        }
    }
}
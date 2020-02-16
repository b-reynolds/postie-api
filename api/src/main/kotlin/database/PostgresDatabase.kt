package database

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin

/**
 * Database
 */
class PostgresDatabase(private val credentials: PostgresCredentials) {
    /**
     * Connects to and returns the data source.
     */
    fun connect(): Jdbi {
        val connectionString = with(credentials) { "$host:$port/postie?user=$user&password=$password" }
        return Jdbi.create(connectionString).apply {
            installPlugin(KotlinSqlObjectPlugin())
            installPlugin(KotlinPlugin())
        }
    }
}

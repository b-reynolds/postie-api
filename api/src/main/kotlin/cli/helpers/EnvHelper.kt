package cli.helpers

import kotlin.system.exitProcess

/**
 * Helper class for retrieving environment variables.
 */
object EnvHelper {
    fun require(name: String): String {
        val value = System.getenv(name)
        if (value == null) {
            System.err.println("Missing environment variable '$name'")
            exitProcess(-1)
        }

        return value
    }

    fun requireInt(name: String): Int {
        val value = require(name).toIntOrNull()
        if (value == null) {
            System.err.println("Environment variable '$name' is not a valid integer")
            exitProcess(-1)
        }

        return value
    }
}

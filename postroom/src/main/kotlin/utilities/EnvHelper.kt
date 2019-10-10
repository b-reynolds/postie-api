package utilities

/**
 * Helper class for retrieving environment variables.
 */
object EnvHelper {
    /**
     * Returns the specified environment variable.
     *
     * If the environment variable does not exist an [IllegalStateException] will be thrown.
     */
    fun get(name: String) = System.getenv(name) ?: error("Failed to read environment variable '$name'")

    /**
     * Returns the specified environment variable.
     *
     * If the environment variable does not exist or type conversion fails an [IllegalStateException] will be thrown.
     */
    fun getInt(name: String): Int {
        val value = System.getenv(name) ?: error("Failed to read environment variable '$name'")
        return value.toIntOrNull() ?: error("Failed to interpret '$name' as type '${Int::class.simpleName}'")
    }
}
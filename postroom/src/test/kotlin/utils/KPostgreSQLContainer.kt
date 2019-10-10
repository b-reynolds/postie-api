package utils

import org.testcontainers.containers.PostgreSQLContainer
import utilities.Resources

class KPostgreSQLContainer : PostgreSQLContainer<KPostgreSQLContainer>() {
    val connectionString
        get() =
            Defaults.DRIVER
                .plus(containerIpAddress)
                .plus(":${getMappedPort(Defaults.EXPOSED_PORT)}")
                .plus("/${Defaults.Credentials.DATABASE}")
                .plus("?user=${Defaults.Credentials.USER}")
                .plus("&password=${Defaults.Credentials.USER}")

    object Defaults {
        const val EXPOSED_PORT = 5432
        const val DRIVER = "jdbc:postgresql://"

        object Credentials {
            const val DATABASE = "postgres"
            const val USER = "postgres"
            const val PASSWORD = "postgres"
        }
    }

    companion object {
        fun testInstance(): KPostgreSQLContainer = KPostgreSQLContainer()
            .withDatabaseName(Defaults.Credentials.DATABASE)
            .withExposedPorts(Defaults.EXPOSED_PORT)
            .withUsername(Defaults.Credentials.USER)
            .withPassword(Defaults.Credentials.PASSWORD)
            .withInitScript(Resources.INIT_SQL)
    }
}
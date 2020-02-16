package cli

import api.Api
import api.initializeDependencies
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.defaultLazy
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import database.PostgresCredentials
import cli.helpers.EnvHelper

private const val ENV_PORT = "POSTIE_PORT"
private const val ENV_POSTIE_API_KEY = "POSTIE_API_KEY"

private const val ENV_POSTGRES_HOST = "POSTIE_POSTGRES_HOST"
private const val ENV_POSTGRES_PORT = "POSTIE_POSTGRES_PORT"
private const val ENV_POSTGRES_USER = "POSTIE_POSTGRES_USER"
private const val ENV_POSTGRES_PASSWORD = "POSTIE_POSTGRES_PASSWORD"

class RunCommand : CliktCommand(
    name = "run",
    help = "Starts the Postie API"
) {
    private val port by option(
        names = *arrayOf("--port"),
        help = "The port Postie will listen on (defaults to environment variable ${ENV_PORT})."
    )
        .int()
        .defaultLazy { EnvHelper.requireInt(ENV_PORT) }

    private val apiKey by option(
        names = *arrayOf("--api-key"),
        help = "Postie API key that will be used to to authenticate requests (defaults to environment variable " +
                "${ENV_POSTIE_API_KEY})."
    )
        .defaultLazy { EnvHelper.require(ENV_POSTIE_API_KEY) }

    private val postgresHost by option(
        names = *arrayOf("--postgres-host"),
        help = "Postie database hostname (defaults to environment variable ${ENV_POSTGRES_HOST})."
    )
        .defaultLazy { EnvHelper.require(ENV_POSTGRES_HOST) }

    private val postgresPort by option(
        names = *arrayOf("--postgres-port"),
        help = "Postie database port (defaults to environment variable ${ENV_POSTGRES_PORT})."
    )
        .int()
        .defaultLazy { EnvHelper.requireInt(ENV_POSTGRES_PORT) }

    private val postgresUser by option(
        names = *arrayOf("--postgres-user"),
        help = "Postie database username (defaults to environment variable ${ENV_POSTGRES_USER})."
    )
        .defaultLazy { EnvHelper.require(ENV_POSTGRES_USER) }

    private val postgresPassword by option(
        names = *arrayOf("--postgres-password"),
        help = "Postie database password (defaults to environment variable ${ENV_POSTGRES_PASSWORD})."
    )
        .defaultLazy { EnvHelper.require(ENV_POSTGRES_PASSWORD) }


    override fun run() {
        initializeDependencies(PostgresCredentials(postgresHost, postgresPort, postgresUser, postgresPassword))

        Api(port, apiKey)
            .start()
    }
}

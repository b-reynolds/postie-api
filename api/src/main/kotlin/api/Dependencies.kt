package api

import database.daos.FilesDao
import database.implementations.JdbiFilesDao
import database.daos.FileTypesDao
import database.implementations.JdbiFileTypesDao
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import database.PostgresCredentials
import database.PostgresDatabase
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initializeDependencies(postgresCredentials: PostgresCredentials) {
    val postgresDatabase = PostgresDatabase(postgresCredentials).connect()

    startKoin {
        modules(
            module {
                single<FilesDao> { JdbiFilesDao(postgresDatabase) }
                single<FileTypesDao> { JdbiFileTypesDao(postgresDatabase) }
                single<ObjectMapper> {
                    jacksonObjectMapper()
                        .enable(SerializationFeature.INDENT_OUTPUT)
                }
            }
        )
    }
}

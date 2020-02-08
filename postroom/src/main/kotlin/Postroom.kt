import api.v1.Path
import api.v1.files.controllers.FilesGetController
import api.v1.files.controllers.FilesPostController
import api.v1.files.daos.implementations.jdbi.JdbiFileDao
import api.v1.files.daos.implementations.jdbi.JdbiFileTypeDao
import api.v1.files.daos.FileDao
import api.v1.files.daos.FileTypeDao
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.plugin.json.JavalinJackson
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.get
import org.koin.dsl.module
import satchel.Satchel
import utilities.ContentType
import utilities.EnvHelper

/**
 * Application entry-point.
 */
fun main() {
    initializeDependencyInjection()
    Api.start()
}

private fun initializeDependencyInjection() {
    val dataSource = Satchel.connect()
    startKoin {
        modules(
            module {
                single<FileDao> {
                    JdbiFileDao(
                        dataSource
                    )
                }
                single<FileTypeDao> {
                    JdbiFileTypeDao(
                        dataSource
                    )
                }
            }
        )
    }
}

private const val ENV_POSTMAN_PORT = "POSTMAN_PORT"

private object Api : KoinComponent {
    fun start() {
        val filesGetController = FilesGetController(get())
        val filesPostController = FilesPostController(get(), get())

        JavalinJackson
            .configure(
                jacksonObjectMapper()
                    .enable(SerializationFeature.INDENT_OUTPUT)
            )

        Javalin
            .create { config ->
                config.defaultContentType = ContentType.Application.JSON
            }
            .routes {
                path(Path.FILES) {
                    path(FilesGetController.Parameter.FileId.name) {
                        get(filesGetController::get)
                    }

                    post(filesPostController::create)
                }
            }
            .start(EnvHelper.getInt(ENV_POSTMAN_PORT))
    }
}

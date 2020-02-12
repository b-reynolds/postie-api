import api.v1.Path
import api.v1.exceptions.ApiException
import api.v1.exceptions.InternalServerException
import api.v1.files.controllers.FilesGetController
import api.v1.files.controllers.FilesPostController
import api.v1.files.daos.implementations.jdbi.JdbiFilesDao
import api.v1.filetypes.daos.implementations.jdbi.JdbiFileTypesDao
import api.v1.files.daos.FilesDao
import api.v1.filetypes.controllers.FileTypeGetController
import api.v1.filetypes.controllers.FileTypesGetController
import api.v1.filetypes.controllers.FileTypesPostController
import api.v1.filetypes.daos.FileTypesDao
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import database.Database
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.plugin.json.JavalinJackson
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.get
import org.koin.dsl.module
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
    val dataSource = Database.connect()
    startKoin {
        modules(
            module {
                single<FilesDao> { JdbiFilesDao(dataSource) }
                single<FileTypesDao> {
                    JdbiFileTypesDao(
                        dataSource
                    )
                }
                single<ObjectMapper> {
                    jacksonObjectMapper()
                        .enable(SerializationFeature.INDENT_OUTPUT)
                }
            }
        )
    }
}

private const val ENV_API_PORT = "API_PORT"

private object Api : KoinComponent {
    fun start() {
        val filesGetController = FilesGetController(get())
        val filesPostController = FilesPostController(get(), get(), get())
        val fileTypesGetController = FileTypesGetController(get())
        val fileTypeGetController = FileTypeGetController(get())
        val fileTypesPostController = FileTypesPostController(get(), get())

        JavalinJackson.configure(get())
        Javalin
            .create { config ->
                config.defaultContentType = ContentType.Application.JSON
            }
            .routes {
                path(Path.VERSION) {
                    path(Path.FILES) {
                        path(FilesGetController.Parameter.FileId.name) {
                            get(filesGetController::get)
                        }

                        post(filesPostController::create)
                    }
                    path(Path.FILE_TYPES) {
                        get(fileTypesGetController::get)
                        post(fileTypesPostController::create)
                        path(FileTypeGetController.Parameter.FileTypeId.name) {
                            get(fileTypeGetController::get)
                        }
                    }
                }
            }
            .exception(ApiException::class.java) { exception, context ->
                context.status(exception.status)
                context.json(exception)
            }
            .exception(Exception::class.java) { _, context ->
                val exception = InternalServerException()

                context.status(exception.status)
                context.json(exception)
            }
            .start(EnvHelper.getInt(ENV_API_PORT))
    }
}

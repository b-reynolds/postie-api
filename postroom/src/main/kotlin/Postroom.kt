import files.controllers.FilesController
import files.daos.JdbiSatchelFileDao
import files.daos.JdbiSatchelFileTypeDao
import files.daos.SatchelFileDao
import files.daos.SatchelFileTypeDao
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
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
                single<SatchelFileDao> { JdbiSatchelFileDao(dataSource) }
                single<SatchelFileTypeDao> { JdbiSatchelFileTypeDao(dataSource) }
            }
        )
    }
}

private const val PATH_FILES = "files"
private const val ENV_POSTMAN_PORT = "POSTMAN_PORT"

private object Api : KoinComponent {
    fun start() {
        val filesController = FilesController(get(), get())

        Javalin
            .create { config ->
                config.defaultContentType = ContentType.Application.JSON
            }
            .routes {
                path(PATH_FILES) {
                    post(filesController::create)
                    path(FilesController.Parameters.FILE_ID) {
                        get(filesController::get)
                    }
                }
            }
            .start(EnvHelper.getInt(ENV_POSTMAN_PORT))
    }
}

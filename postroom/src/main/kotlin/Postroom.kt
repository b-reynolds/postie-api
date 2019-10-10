import files.controllers.FilesController
import files.daos.JdbiSatchelFileDao
import files.daos.JdbiSatchelFileTypeDao
import files.daos.SatchelFileDao
import files.daos.SatchelFileTypeDao
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import org.koin.core.context.startKoin
import org.koin.dsl.module
import satchel.Satchel
import utilities.ContentType
import utilities.EnvHelper

/**
 * Application entry-point.
 */
fun main() {
    initializeDependencyInjection()
    startApi()
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

private fun startApi() {
    Javalin
        .create { config ->
            config.defaultContentType = ContentType.Application.JSON
        }
        .routes {
            path(PATH_FILES) {
                post(FilesController()::create)
                path(FilesController.Parameters.FILE_ID) {
                    get(FilesController()::get)
                }
            }
        }
        .start(EnvHelper.getInt(ENV_POSTMAN_PORT))
}
import files.controllers.FilesController
import files.daos.JdbiSatchelFileDao
import files.daos.SatchelFileDao
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import org.koin.core.context.startKoin
import org.koin.dsl.module
import satchel.Satchel
import utilities.ContentType

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
            }
        )
    }
}

private const val PATH_FILES = "files"
private const val PORT = 7000

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
        .start(PORT)
}
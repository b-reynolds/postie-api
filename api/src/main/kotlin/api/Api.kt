package api

import api.helpers.ContentType
import api.v1.Path
import api.v1.exceptions.ApiException
import api.v1.exceptions.InternalServerException
import api.v1.routes.auth.controllers.AuthenticationController
import api.v1.routes.files.controllers.FilesGetController
import api.v1.routes.files.controllers.FilesPostController
import api.v1.routes.filetypes.controllers.FileTypeGetController
import api.v1.routes.filetypes.controllers.FileTypesGetController
import api.v1.routes.filetypes.controllers.FileTypesPostController
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.plugin.json.JavalinJackson
import org.koin.core.KoinComponent
import org.koin.core.get

class Api(private val port: Int, private val apiKey: String) : KoinComponent {
    fun start() {
        val authenticationController = AuthenticationController(apiKey)

        val filesGetController = FilesGetController(get())
        val filesPostController = FilesPostController(get(), get(), get())
        val fileTypeGetController = FileTypeGetController(get())
        val fileTypesGetController = FileTypesGetController(get())
        val fileTypesPostController = FileTypesPostController(get(), get())

        JavalinJackson.configure(get())
        Javalin
            .create { config ->
                config.defaultContentType = ContentType.Application.JSON
            }
            .routes {
                path(Path.VERSION) {
                    path(Path.FILES) {
                        before(authenticationController::verify)

                        path(FilesGetController.Parameter.FileId.name) {
                            get(filesGetController::get)
                        }

                        post(filesPostController::create)
                    }
                    path(Path.FILE_TYPES) {
                        before(authenticationController::verify)

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
            .start(port)
    }
}

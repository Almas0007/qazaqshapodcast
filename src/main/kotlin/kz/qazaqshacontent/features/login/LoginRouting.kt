package kz.qazaqshacontent.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import kz.qazaqshacontent.features.login.LoginController


fun Application.configureLoginRouting() {
    routing {
        get("/login") {
            val loginController = LoginController(call)
            loginController.performLogin()
        }
    }
}

package kz.qazaqshacontent.plugins


import io.ktor.server.routing.*
import io.ktor.server.application.*
import kz.qazaqshacontent.features.register.RegisterController


fun Application.configureRegisterRouting() {
    routing {
        post("/register") {
            val registerController = RegisterController(call)
            registerController.registerNewUser()
        }
    }

}


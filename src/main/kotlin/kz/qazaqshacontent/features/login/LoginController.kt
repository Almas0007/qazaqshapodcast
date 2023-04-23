package kz.qazaqshacontent.features.login

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kz.qazaqshacontent.cache.InMemoryCache
import kz.qazaqshacontent.database.tokens.Tokens
import kz.qazaqshacontent.database.users.Users

class LoginController(private val call: ApplicationCall) {

    suspend fun performLogin() {
        val receive = call.receive<LoginReceiveRemote>()
        val userDTO = Users.fetchUser(receive.email)
        val tokenDTO = Tokens.fetchToken(receive.email)

        if (userDTO == null) {
            call.respond(HttpStatusCode.BadRequest, "User not found")
        } else {
            if (userDTO.password == receive.password) {
                call.respond(LoginResponseRemote(token = tokenDTO?.token ?: "Token no generated"))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid password")
            }
        }
    }
}

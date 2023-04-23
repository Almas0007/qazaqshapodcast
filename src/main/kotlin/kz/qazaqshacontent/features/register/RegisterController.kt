package kz.qazaqshacontent.features.register

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kz.qazaqshacontent.database.tokens.TokenDTO
import kz.qazaqshacontent.database.tokens.Tokens
import kz.qazaqshacontent.database.users.UserDTO
import kz.qazaqshacontent.database.users.Users
import kz.qazaqshacontent.utils.isValidEmail
import org.jetbrains.exposed.exceptions.ExposedSQLException
import java.util.*

class RegisterController(private val call: ApplicationCall) {

    suspend fun registerNewUser() {
        val registerReceiveRemote = call.receive<RegisterReceiveRemote>()
        if (!registerReceiveRemote.email.isValidEmail()) {
            call.respond(HttpStatusCode.BadRequest, "Incorrect email")
        }

        val userDTO = Users.fetchUser(registerReceiveRemote.email)
        if (userDTO != null) {
            call.respond(HttpStatusCode.Conflict, "User already User DTO! exists")
        } else {
            val token = UUID.randomUUID().toString()
            try {
                Users.insert(
                    UserDTO(
                        email = registerReceiveRemote.email,
                        name = registerReceiveRemote.name,
                        password = registerReceiveRemote.password
                    )
                )
                call.respond(RegisterResponseRemote(token = token))

            } catch (e: ExposedSQLException) {
                call.respond(HttpStatusCode.Conflict, "User already Catch exists")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Can't create user ${e.localizedMessage}")
            }

            Tokens.insert(
                TokenDTO(
                    rowId = UUID.randomUUID().toString(),
                    email = registerReceiveRemote.email,
                    token = token
                )
            )


        }

    }

}
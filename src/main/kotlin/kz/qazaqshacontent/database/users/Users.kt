package kz.qazaqshacontent.database.users

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object Users : Table() {
    private val email = Users.varchar("email", 50)
    private val name = Users.varchar("name", 25)
    private val password = Users.varchar("passwords", 50)

    fun insert(userDTO: UserDTO) {
        transaction {
            Users.insert {
                it[email] = userDTO.email
                it[name] = userDTO.name
                it[password] = userDTO.password
            }
        }
    }

    fun fetchUser(email: String): UserDTO? {
        return try {
            transaction {
                val userModel =Users.select { Users.email.eq(email)}.single()
                UserDTO(
                    email = userModel[Users.email],
                    name = userModel[name],
                    password = userModel[password]
                )
            }
        } catch (e: Exception) {
            null
        }

    }
}
package kz.qazaqshacontent.database.tokens

import kz.qazaqshacontent.database.users.UserDTO
import kz.qazaqshacontent.database.users.Users
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Tokens : Table() {
    private val id = Tokens.varchar("id", 50)
    private val email = Tokens.varchar("email", 50)
    private val token = Tokens.varchar("token", 50)

    fun insert(tokenDTO: TokenDTO) {
        transaction {
            Tokens.insert {
                it[id] = tokenDTO.rowId
                it[email] = tokenDTO.email
                it[token] = tokenDTO.token
            }
        }
    }

    fun fetchToken(email: String): TokenDTO? {
        return try {
            transaction {
                val tokenResponse = Tokens.select { Tokens.email.eq(email) }.single()
                    TokenDTO(
                    rowId = tokenResponse[Tokens.id],
                    email = tokenResponse[Tokens.email],
                    token = tokenResponse[Tokens.token]
                )
            }
        } catch (e: Exception) {
            null
        }
    }

    fun fetchTokens(): List<TokenDTO> {
        return try {
            transaction {
                Tokens.selectAll().toList()
                    .map {
                        TokenDTO(
                            rowId = it[Tokens.id],
                            email = it[Tokens.email],
                            token = it[Tokens.token]
                        )
                    }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

}

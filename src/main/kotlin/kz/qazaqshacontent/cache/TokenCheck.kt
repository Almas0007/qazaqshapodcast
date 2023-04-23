package kz.qazaqshacontent.cache

import kz.qazaqshacontent.database.tokens.Tokens

object TokenCheck {
    fun isTokenValid(token: String): Boolean = Tokens.fetchTokens().firstOrNull { it.token == token } != null

}
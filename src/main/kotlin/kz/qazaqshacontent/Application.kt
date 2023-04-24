package kz.qazaqshacontent

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kz.qazaqshacontent.features.channel.configureRoutingChannels
import kz.qazaqshacontent.features.video.configureVideosRouting
import kz.qazaqshacontent.plugins.*
import org.jetbrains.exposed.sql.Database

fun main() {

    Database.connect(createDataSource())

//    Database.connect(
//        "jdbc:postgresql://postgres:6FWdfpSCXIOE34Hvb0GH@containers-us-west-182.railway.app:5641/railway",
//        driver = "org.postgresql.Driver",
//        "postgres",
//        "6FWdfpSCXIOE34Hvb0GH"
//    )
//    embeddedServer(Netty, port = 5641, host = "0.0.0.0", module = Application::module)
//        .start(wait = true)
}

fun Application.module() {
    configureLoginRouting()
    configureRegisterRouting()
    configureSerialization()
    configureRouting()
    configureRoutingChannels()
    configureVideosRouting()
}

private fun createDataSource(): HikariDataSource {
    val config = HikariConfig()

    config.apply {
        password = "6FWdfpSCXIOE34Hvb0GH"
        username = "postgres"
        jdbcUrl = "jdbc:postgresql://containers-us-west-182.railway.app:5641/railway"
        driverClassName = "org.postgresql.Driver"
        maximumPoolSize = 3
        isAutoCommit = true
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    }
    return HikariDataSource(config)
}
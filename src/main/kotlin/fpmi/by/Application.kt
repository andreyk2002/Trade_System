package fpmi.by

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import fpmi.by.plugins.*
import io.ktor.application.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(){
    embeddedServer(Netty, port = 8080) {
        configureSecurity()
        configureTemplating()
        configureSerialization()
        configureRouting()
        configureDb()
    }.start(wait = true)
}

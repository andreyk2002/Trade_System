package fpmi.by.plugins

import fpmi.by.dao.ConnectionManager
import io.ktor.application.*

fun Application.configureDb(){
    val connectionManager = ConnectionManager()
    val userRepository = connectionManager.getUserRepository()
}
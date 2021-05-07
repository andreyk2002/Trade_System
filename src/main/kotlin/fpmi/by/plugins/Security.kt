package fpmi.by.plugins

import fpmi.by.entity.User
import io.ktor.sessions.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.configureSecurity() {

    install(Sessions) {
        cookie<UserSession>("MY_SESSION") {
            cookie.extensions["SameSite"] = "lax"
        }
    }
}

data class UserSession(val user: User, val count: Int = 0)

data class OrganizationSession(val organizationId: Int, val count: Int = 0)

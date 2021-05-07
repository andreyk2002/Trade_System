package fpmi.by

import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.auth.*
import io.ktor.util.*
import io.ktor.sessions.*
import freemarker.cache.*
import io.ktor.freemarker.*
import io.ktor.jackson.*
import com.fasterxml.jackson.databind.*
import io.ktor.features.*
import io.ktor.http.cio.websocket.*
import io.ktor.websocket.*
import java.time.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import fpmi.by.plugins.configureRouting
import kotlin.test.*
import io.ktor.server.testing.*

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ configureRouting() }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("Hello World!", response.content)
            }
        }
    }
}
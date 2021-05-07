package fpmi.by.plugins

import fpmi.by.entity.User
import freemarker.cache.*
import freemarker.core.HTMLOutputFormat
import io.ktor.freemarker.*
import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.configureTemplating() {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
        outputFormat = HTMLOutputFormat.INSTANCE
    }

    routing {

    }
}

data class IndexData(val items: List<Int>)

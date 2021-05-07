package fpmi.by.plugins

import fpmi.by.entity.Order
import fpmi.by.entity.Product
import fpmi.by.entity.User
import fpmi.by.service.CategoryService
import fpmi.by.service.OrderService
import fpmi.by.service.ProductService
import fpmi.by.service.UserService
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.ktor.util.*
import java.math.BigDecimal


fun Application.configureRouting() {

    val productService = ProductService()
    val userService = UserService()
    val categoryService = CategoryService()
    val orderService = OrderService()


    routing {
        static("/styles") {
            resources("/styles")
        }


        get("/") {
            val session = call.sessions.get<UserSession>()
            val user = session?.user ?: User(name = "guest")
            val products = productService.getAll()
            val page = FreeMarkerContent("index.ftl", mapOf("user" to user, "products" to products), "")
            call.respond(page)
        }

        get("/login") {
            val loginPage = FreeMarkerContent("login.ftl", null)
            call.respond(loginPage)
        }

        get("/logout") {
            call.sessions.clear<UserSession>()
            call.respondRedirect("/")
        }


        post("/login") {
            val parameters = call.receiveParameters()
            val password = parameters["password"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val login = parameters["login"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val user = userService.login(login, password)
            if (user != null) {
                val session = call.sessions.get<UserSession>() ?: UserSession(user)
                call.sessions.set(session.copy(count = session.count + 1))
                call.respondRedirect("/")
            } else {
                call.respondText { "Wrong username or password" }
            }

        }

        post("/search") {
            val parameters = call.receiveParameters()
            val search = parameters["searchString"]
            search ?: call.respondText { "Search film should be not empty" }
            val foundProducts = productService.findProducts(search!!)
            val page = FreeMarkerContent("index.ftl", mapOf("products" to foundProducts), "")
            call.respond(page)
        }

        get("/create") {
            val categories = categoryService.getAll()
            val createPage = FreeMarkerContent("create.ftl", mapOf("categories" to categories))
            call.respond(createPage)
        }

        post("/addItem") {
            val parameters = call.receiveParameters()
            val name = parameters["name"] ?: call.respond(HttpStatusCode.BadRequest)
            val price = parameters["price"] ?: call.respond(HttpStatusCode.BadRequest)
            val categoryId = parameters["categoryId"] ?: call.respond(HttpStatusCode.BadRequest)
            val article = parameters["article"] ?: call.respond(HttpStatusCode.BadRequest)
            val userSession = call.sessions.get<UserSession>()
            val organizationId = userSession?.user?.organizationId
            val product = Product(
                name = name as String,
                price = BigDecimal(price as String),
                article = article as String,
                categoryId = (categoryId as String).toInt(),
                organizationId = organizationId!!
            )
            productService.addProduct(product)
            call.respondRedirect("/")
        }

        get("/edit") {
            val parameters = call.parameters
            val itemId = parameters["itemId"]
            if (itemId == null) {
                call.respond(HttpStatusCode.BadRequest)
            } else {
                val item = productService.getById(itemId)
                val categories = categoryService.getAll()
                val page = FreeMarkerContent("edit.ftl", mapOf("item" to item, "categories" to categories))
                call.respond(page)
            }
        }

        post("/edit") {
            val parameters = call.receiveParameters()
            val itemId = parameters["itemId"] as String
            val name = parameters["name"]
            val price = parameters["price"]
            val categoryId = parameters["categoryId"] as String
            val newProduct = Product(
                id = itemId.toInt(),
                name = name!!,
                price = BigDecimal(price),
                categoryId = categoryId.toInt(),
            )
            productService.updateProduct(newProduct)
            call.respondRedirect("/")
        }

        get("/buy") {
            val parameters = call.parameters
            val itemIdParam = parameters["itemId"] as String
            val itemId = itemIdParam.toInt()
            val session = call.sessions.get<UserSession>()
            val userId = session?.user?.id
            if (userId != null) {
                val order = Order(productId = itemId, userId = userId)
                orderService.addOrder(order)
                call.respondText { "Product was successfully ordered" }
            } else {
                call.respond(HttpStatusCode.BadRequest)
            }

        }

        get("/research"){
            val orders = orderService.getAll()
            val page = FreeMarkerContent("analitics.ftl", mapOf("orders" to orders))
            call.respond(page)
        }
    }
}


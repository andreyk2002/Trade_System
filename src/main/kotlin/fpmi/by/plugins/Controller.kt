package fpmi.by.plugins

import fpmi.by.entity.Order
import fpmi.by.entity.OrderDto
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
import org.joda.time.DateTime
import java.math.BigDecimal
import kotlin.math.abs


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
                val errorMessage = "Wrong username or password"
                val loginPage = FreeMarkerContent("login.ftl", mapOf("errorMessage" to errorMessage))
                call.respond(loginPage)
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

        get("/research") {
            val categories = categoryService.getAll()
            val orders = orderService.getAll()
            val ordersPoints = formOrdersPoints(orders)
            val page = FreeMarkerContent("analitics.ftl", mapOf("orders" to ordersPoints, "categories" to categories))
            call.respond(page)
        }

        post("/searchCategories") {
            val parameters = call.receiveParameters()
            val minDate = DateTime.parse(parameters["minDate"] as String)
            val maxDate = DateTime.parse(parameters["maxDate"] as String)
            val categoryId = (parameters["categoryId"] as String).toInt()
            val searchResults = orderService.searchByDateAndCategory(categoryId, minDate, maxDate)
            val page = FreeMarkerContent("orders.ftl", mapOf("orders" to searchResults))
            call.respond(page)
        }

        post("/searchUsers") {
            val parameters = call.receiveParameters()
            val productName = parameters["productName"] as String
            val usersByProductName = userService.searchByProductName(productName).distinctBy { it.id }
            val page = FreeMarkerContent("users.ftl", mapOf("users" to usersByProductName))
            call.respond(page)
        }
    }
}

fun formOrdersPoints(orders: List<OrderDto>): Map<String, String> {
    val sortedByDate = orders.sortedBy { it.date }
    val minData = sortedByDate.first().date.millis
    val maxPrice = orders.maxByOrNull { it.price }!!
    val delta = subtractDates(sortedByDate.first().date, sortedByDate.last().date)

    val productsWithOrders = mutableMapOf<String, List<OrderDto>>()
    for (order in sortedByDate) {
        val productName = order.productName
        val list = productsWithOrders[productName]?.toMutableList()
        if (list != null) {
            list += order
            productsWithOrders[productName] = list
        } else {
            val newList = mutableListOf(order)
            productsWithOrders[productName] = newList
        }

    }

    //first point - data, second - frequency

    val productsPoints = mutableMapOf<String, String>()
    for (product in productsWithOrders.keys) {
        var points = ""
        val currentOrders = productsWithOrders[product]!!
        for (i in 0..200 step 20) {
            val countInDatePeriod = currentOrders.filter { orderDto ->
                abs(getPoint(orderDto, minData, delta) - i) < 20
            }.count()
            val ratioCount = 200 - countInDatePeriod * 200.0 / currentOrders.size
            points += "$i,$ratioCount "
        }
        productsPoints[product] = points.trim()
    }
    return productsPoints
}

private fun getPoint(orders: OrderDto, minData: Long, delta: Long) =
    200.0 * (orders.date.millis - minData) / delta

fun subtractDates(first: DateTime, last: DateTime): Long {
    return last.millis - first.millis
}

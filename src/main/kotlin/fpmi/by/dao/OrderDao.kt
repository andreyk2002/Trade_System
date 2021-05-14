package fpmi.by.dao

import fpmi.by.dao.tables.Orders
import fpmi.by.dao.tables.Products
import fpmi.by.dao.tables.Users
import fpmi.by.entity.Order
import fpmi.by.entity.OrderDto
import fpmi.by.entity.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.transaction
import org.joda.time.DateTime

class OrderDao(private val db: Database) : Dao<Order> {
    override fun init() {
        TODO("Not yet implemented")
    }

    override suspend fun create(item: Order): Unit = transaction(db) {
        val date = DateTime.now()
        Orders.insert {
            it[productId] = item.productId
            it[userId] = item.userId
            it[orderDate] = date
        }
    }

    override suspend fun update(item: Order) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(item: Order) {
        TODO("Not yet implemented")
    }

    override suspend fun get(id: Int): Order? {
        TODO("Not yet implemented")
    }

    suspend fun getAllOrders(): List<OrderDto> = transaction(db) {
        (Orders innerJoin Products).selectAll().map {
            OrderDto(
                productName = it[Products.name],
                price = it[Products.price],
                date = it[Orders.orderDate]
            )
        }
    }

    override fun close() {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): List<Order> {
        TODO("Not yet implemented")
    }

    suspend fun getByDateAndCategory(categoryId: Int, minDate: DateTime, maxDate: DateTime) : List<OrderDto> = transaction(db){
        (Orders innerJoin Products).select{(Products.categoryId eq categoryId) and (Orders.orderDate.between(minDate, maxDate))}.map {
            OrderDto(
                productName = it[Products.name],
                price = it[Products.price],
                date = it[Orders.orderDate]
            )
        }
    }

}

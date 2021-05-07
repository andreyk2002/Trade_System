package fpmi.by.service

import fpmi.by.dao.ConnectionManager
import fpmi.by.dao.OrderDao
import fpmi.by.dao.UserDao
import fpmi.by.entity.Order
import fpmi.by.entity.OrderDto
import kotlinx.coroutines.runBlocking

class OrderService {

    private val orderDao: OrderDao

    init {
        val connectionManager = ConnectionManager()
        orderDao = connectionManager.getOrderDao()
    }

    fun addOrder(order: Order) = runBlocking {
        orderDao.create(order)
    }

    fun getAll(): List<OrderDto> = runBlocking {
        orderDao.getAllOrders()
    }

}

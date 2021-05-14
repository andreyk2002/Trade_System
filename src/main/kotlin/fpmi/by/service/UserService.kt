package fpmi.by.service

import fpmi.by.dao.ConnectionManager
import fpmi.by.dao.UserDao
import fpmi.by.entity.User
import kotlinx.coroutines.runBlocking

class UserService {


    private val userDao: UserDao

    init {
        val connectionManager = ConnectionManager()
        userDao = connectionManager.getUserRepository()
    }

    fun login(login: String, password: String): User? = runBlocking {
        userDao.getByLoginAndPassword(login, password)
    }

    fun searchByProductName(productName: String) : List<User> = runBlocking {
        userDao.getByProductName(productName)
    }

}

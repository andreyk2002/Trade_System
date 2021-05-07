package fpmi.by.service

import fpmi.by.dao.ConnectionManager
import fpmi.by.dao.UserDao
import fpmi.by.entity.User
import kotlinx.coroutines.runBlocking

class UserService {


    private val userRepository: UserDao

    init {
        val connectionManager = ConnectionManager()
        userRepository = connectionManager.getUserRepository()
    }

    fun login(login: String, password: String): User? = runBlocking {
        userRepository.getByLoginAndPassword(login, password)
    }

}

package fpmi.by.dao

import fpmi.by.dao.tables.Orders
import fpmi.by.dao.tables.Products
import fpmi.by.dao.tables.Users
import fpmi.by.entity.User
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.transaction
import java.math.BigInteger
import java.security.MessageDigest

class UserDao(private val db: Database) : Dao<User> {
    override fun init() {
        SchemaUtils.createMissingTablesAndColumns(Users)
    }

    override fun close() {
        TODO("Not yet implemented")
    }

    override suspend fun create(item: User) {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: User) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(item: User) {
        TODO("Not yet implemented")
    }

    override suspend fun get(id: Int): User? {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): List<User> {
        TODO("Not yet implemented")
    }

    suspend fun getByLoginAndPassword(login: String, password: String): User? = transaction(db) {
        Users.select { (Users.name eq login) and (Users.password eq md5(password)) }.map {
            User(name = it[Users.name], role = it[Users.role], id = it[Users.id])
        }.firstOrNull()
    }

    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

    suspend fun getByProductName(productName: String): List<User> = transaction(db) {
        (Users innerJoin Orders innerJoin Products).select { (Products.name eq productName) }.map {
            User(name = it[Users.name], role = it[Users.role], id = it[Users.id])
        }
    }

}
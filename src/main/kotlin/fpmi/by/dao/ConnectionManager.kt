package fpmi.by.dao

import org.jetbrains.exposed.sql.Database

class ConnectionManager {


    object Connection {
        val db = Database.connect(
            url = "jdbc:mysql://database-2.cdlfbexb3xhn.us-east-2.rds.amazonaws.com/TradeSystem",
            driver = "org.mariadb.jdbc.Driver",
            user = "admin",
            password = "djpk03685v2",
        )
    }


    fun getUserRepository(): UserDao {
        return UserDao(Connection.db)
    }

    fun getProductRepository(): ProductDao {
        return ProductDao(Connection.db)
    }

    fun getCategoryRepository(): CategoryDao {
        return CategoryDao(Connection.db)
    }

    fun getOrderDao(): OrderDao {
        return OrderDao(Connection.db)
    }
}
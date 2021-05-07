package fpmi.by.dao

import fpmi.by.dao.tables.ProductCategories
import fpmi.by.entity.Category
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.transaction

class CategoryDao(val db: Database) : Dao<Category> {
    override fun init() {
        SchemaUtils.createMissingTablesAndColumns(ProductCategories)
    }

    override suspend fun create(item: Category) {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: Category) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(item: Category) {
        TODO("Not yet implemented")
    }

    override suspend fun get(id: Int): Category? {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): List<Category> = transaction(db) {
       ProductCategories.selectAll().map {
           Category(it[ProductCategories.name], it[ProductCategories.id])
       }
    }

    override fun close() {
        TODO("Not yet implemented")
    }

}

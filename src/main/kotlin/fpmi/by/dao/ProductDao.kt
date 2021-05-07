package fpmi.by.dao

import fpmi.by.dao.tables.Products
import fpmi.by.entity.Product
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.transaction

class ProductDao(private val db: Database) : Dao<Product> {

    override fun init() {
        SchemaUtils.createMissingTablesAndColumns(Products)
    }

    override suspend fun create(item: Product): Unit = transaction(db) {
        Products.insert {
            it[name] = item.name
            it[article] = item.article
            it[categoryId] = item.categoryId
            it[organizationId] = item.organizationId
            it[price] = item.price
        }
    }

    override suspend fun update(item: Product): Unit = transaction(db) {
        Products.update({ Products.id eq item.id }) {
            it[name] = item.name
            it[price] = item.price
            it[categoryId] = item.categoryId
        }
    }

    override suspend fun delete(item: Product) = transaction(db) {
        TODO("Not yet implemented")
    }

    override suspend fun get(id: Int): Product? = transaction(db) {
        Products.select { Products.id eq id }.map {
            toProduct(it)
        }.firstOrNull()
    }

    suspend fun findByName(searchedName: String): List<Product> = transaction(db) {
        val select = Products.select { Products.name like "%$searchedName%" }
        mapToList(select)
    }

    private fun mapToList(select: Query): List<Product> = select.map {
        toProduct(it)
    }

    private fun toProduct(it: ResultRow) = Product(
        it[Products.name],
        it[Products.price],
        it[Products.article],
        it[Products.categoryId],
        it[Products.organizationId],
        it[Products.id]
    )

    override suspend fun getAll(): List<Product> = transaction(db) {
        val selectAll = Products.selectAll()
        mapToList(selectAll)
    }

    override fun close() {
        TODO("Not yet implemented")
    }
}
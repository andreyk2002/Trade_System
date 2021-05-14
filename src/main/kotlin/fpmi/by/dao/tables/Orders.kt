package fpmi.by.dao.tables

import fpmi.by.dao.tables.Products.autoIncrement
import fpmi.by.dao.tables.Products.primaryKey
import org.jetbrains.exposed.sql.Table

object Orders : Table(){
    val id = integer("ID").primaryKey().autoIncrement()
    val userId = integer("UserID").references(Users.id)
    val productId = integer("ProductID").references(Products.id)
    val orderDate = date("Date")
}

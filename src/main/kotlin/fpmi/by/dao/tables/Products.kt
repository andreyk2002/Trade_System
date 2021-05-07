package fpmi.by.dao.tables

import org.jetbrains.exposed.sql.Table

object Products : Table() {
    val id = integer("ID").primaryKey().autoIncrement()
    val organizationId = integer("OrganizationID")
    val categoryId = integer("CategoryId")
    val name = varchar("Name", 255)
    val price = decimal("price", 5,5)
    val article = varchar("Article", 255)
}
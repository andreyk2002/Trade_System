package fpmi.by.dao.tables

import fpmi.by.dao.tables.Users.autoIncrement
import fpmi.by.dao.tables.Users.primaryKey
import fpmi.by.entity.UserRole
import org.jetbrains.exposed.sql.Table

object ProductCategories : Table() {
    val id = integer("ID").primaryKey().autoIncrement()
    val name = varchar("Name",255)
}

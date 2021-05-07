package fpmi.by.dao.tables

import fpmi.by.entity.UserRole
import org.jetbrains.exposed.sql.Table

object Users : Table() {
    val id = integer("ID").primaryKey().autoIncrement()
    val passwordId = integer("PasswordID")
    val name = varchar("Name",255)
    val address = text("Address")
    val role = enumerationByName("Role", 10, UserRole::class)
    val password = varchar("Parole",255)
}

package fpmi.by.dao

import fpmi.by.dao.spec.ItemSpecification
import fpmi.by.entity.Product
import io.ktor.utils.io.core.Closeable


interface Dao<T>: Closeable {
    fun init()
    suspend fun create(item : T)
    suspend fun update(item : T)
    suspend fun delete(item: T)
    suspend fun get(id : Int): T?
    suspend fun getAll() : List<T>
}
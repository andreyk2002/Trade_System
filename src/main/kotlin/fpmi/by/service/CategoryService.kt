package fpmi.by.service

import fpmi.by.dao.CategoryDao
import fpmi.by.dao.ConnectionManager
import fpmi.by.dao.UserDao
import fpmi.by.entity.Category
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CategoryService {

    private val categoryDao: CategoryDao

    init {
        val connectionManager = ConnectionManager()
        categoryDao = connectionManager.getCategoryRepository()
    }
    fun getAll(): List<Category> {
        val categories = mutableListOf<Category>()
        runBlocking {
            launch {
                val allCategories = categoryDao.getAll()
                categories.addAll(allCategories)
            }
        }
        return categories
    }


}

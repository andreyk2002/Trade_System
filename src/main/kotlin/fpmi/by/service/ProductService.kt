package fpmi.by.service

import fpmi.by.dao.ConnectionManager
import fpmi.by.dao.ProductDao
import fpmi.by.entity.Product
import fpmi.by.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProductService() {
    private val productRepository: ProductDao

    init {
        val connectionManager = ConnectionManager()
        productRepository = connectionManager.getProductRepository()
    }

    fun findProducts(search: String): List<Product> = runBlocking {
        productRepository.findByName(search)
    }


    fun getAll(): List<Product> {
        val results = mutableListOf<Product>()
        runBlocking {
            launch(Dispatchers.Default) {
                val products = productRepository.getAll()
                results.addAll(products)
            }
        }
        return results
    }

    fun addProduct(product: Product) = runBlocking {
        launch {
            productRepository.create(product)
        }
    }

    fun getById(itemId: String): Product? = runBlocking {
        val id = itemId.toInt()
        productRepository.get(id)
    }

    fun updateProduct(newProduct: Product) = runBlocking {
        productRepository.update(newProduct)
    }


}

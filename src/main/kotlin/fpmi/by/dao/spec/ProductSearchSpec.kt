package fpmi.by.dao.spec

import fpmi.by.entity.Product

class ProductSearchSpec(private val productName: String) : ItemSpecification<Product> {
    override fun specify(item: Product): Boolean {
        return item.name == productName
    }
}
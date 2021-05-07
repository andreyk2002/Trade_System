package fpmi.by.entity

import java.math.BigDecimal

class Product(
    val name: String,
    val price: BigDecimal,
    val article: String = "",
    val categoryId: Int,
    val organizationId: Int = 0,
    val id: Int = 0
) {

}

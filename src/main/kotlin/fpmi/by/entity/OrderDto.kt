package fpmi.by.entity

import org.joda.time.DateTime
import java.math.BigDecimal

class OrderDto(val productName : String, val price: BigDecimal, val date: DateTime) {
}
package nearme.restaurant.com.restaurantsnearme.utils

infix fun Any?.ifNull(block: () -> Unit) {
    if (this == null) block()
}
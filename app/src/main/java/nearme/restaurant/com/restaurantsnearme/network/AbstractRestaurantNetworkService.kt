package nearme.restaurant.com.restaurantsnearme.network

import android.content.Context
import io.reactivex.Observable
import nearme.restaurant.com.restaurantsnearme.model.RestaurantDetailsModel

abstract class AbstractRestaurantNetworkService(context: Context) {
    abstract fun getRestaurantDetails(postcode: String): Observable<RestaurantDetailsModel.Restaurants>
}
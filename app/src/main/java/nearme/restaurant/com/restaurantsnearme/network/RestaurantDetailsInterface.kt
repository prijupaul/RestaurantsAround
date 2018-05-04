package nearme.restaurant.com.restaurantsnearme.network

import io.reactivex.Observable
import nearme.restaurant.com.restaurantsnearme.model.RestaurantDetailsModel
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantDetailsInterface {

    @GET("restaurants")
    fun getAccountDetails(@Query("q") postcode: String): Observable<RestaurantDetailsModel.Restaurants>

}
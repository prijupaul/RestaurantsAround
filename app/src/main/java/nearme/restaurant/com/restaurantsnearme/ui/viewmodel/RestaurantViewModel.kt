package nearme.restaurant.com.restaurantsnearme.ui.viewmodel

import android.arch.lifecycle.ViewModel
import nearme.restaurant.com.restaurantsnearme.model.RestaurantDetailsModel

class RestaurantViewModel : ViewModel() {
    var restaurantsDetails: RestaurantDetailsModel.Restaurants? = null
}
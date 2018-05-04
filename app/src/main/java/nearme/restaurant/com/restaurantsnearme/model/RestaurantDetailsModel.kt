package nearme.restaurant.com.restaurantsnearme.model

object RestaurantDetailsModel {

    data class Restaurants(var Restaurants: List<RestaurantsDetails>)
    data class RestaurantsDetails(val Score: Double,
                                  val DriveDistance: Double,
                                  val Name: String,
                                  val Address: String,
                                  val Postcode: String,
                                  val City: String,
                                  val RatingAverage: Float,
                                  val Logo: List<Logo>,
                                  val CuisineTypes: List<CuisineTypes>)

    data class Logo(val StandardResolutionURL: String)
    data class CuisineTypes(val Name: String)

}
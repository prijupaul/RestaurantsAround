package nearme.restaurant.com.restaurantsnearme.network

import android.content.Context
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import nearme.restaurant.com.restaurantsnearme.R
import nearme.restaurant.com.restaurantsnearme.model.RestaurantDetailsModel
import java.io.InputStream
import java.io.InputStreamReader

class FakeRestaurantNetworkService(private val context: Context) : AbstractRestaurantNetworkService(context) {

    var triggerError: Boolean = false

    override fun getRestaurantDetails(postcode: String): Observable<RestaurantDetailsModel.Restaurants> {
//        val inputStream: InputStream = context.resources.openRawResource(
//                context.resources.getIdentifier("response", "raw", context.packageName))

        if (triggerError) {
            return Observable.error {
                Throwable("Simulated error")
            }
        }
        val inputStream: InputStream = context.resources.openRawResource(R.raw.response)

        val transactionResult = GsonBuilder()
                .enableComplexMapKeySerialization()
                .setLenient()
                .create().fromJson(InputStreamReader(inputStream), RestaurantDetailsModel.Restaurants::class.java)
        return Observable.just(transactionResult)
    }
}
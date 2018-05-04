package nearme.restaurant.com.restaurantsnearme.network

import android.content.Context
import io.reactivex.Observable
import nearme.restaurant.com.restaurantsnearme.BuildConfig
import nearme.restaurant.com.restaurantsnearme.model.RestaurantDetailsModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class RestaurantNetworkService(context: Context) : AbstractRestaurantNetworkService(context) {

    private val restaurantDetailsIntrfc by lazy {
        create()
    }

    private fun create(): RestaurantDetailsInterface {

        val httpClient = OkHttpClient.Builder()

        fun addHeader() {
            httpClient.addInterceptor({ chain ->
                val original = chain.request()
                val request = original.newBuilder()
                        .header("Accept-Tenant", "uk")
                        .header("Accept-Language", "en-GB")
                        .header("Authorization", "Basic VGVjaFRlc3Q6bkQ2NGxXVnZreDVw")
                        .header("Host", "public.je-apis.com")
                        .method(original.method(), original.body())
                        .build()
                chain.proceed(request);
            })
        }

        fun addLogging() {
            val loggingInterceptor =  HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(loggingInterceptor)
        }

        addHeader()
        if (BuildConfig.DEBUG) {
            addLogging()
        }

        val client = httpClient.build()
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://public.je-apis.com")
                .client(client)
                .build()

        return retrofit.run {
            retrofit.create(RestaurantDetailsInterface::class.java)
        }
    }

    override fun getRestaurantDetails(postcode: String): Observable<RestaurantDetailsModel.Restaurants> =
            restaurantDetailsIntrfc.getAccountDetails(postcode)
}
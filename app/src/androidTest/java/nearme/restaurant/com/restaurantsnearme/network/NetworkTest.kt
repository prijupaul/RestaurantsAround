package nearme.restaurant.com.restaurantsnearme.network

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.math.BigDecimal

@RunWith(AndroidJUnit4::class)
class NetworkTest {

    lateinit var mockNetworkService: AbstractRestaurantNetworkService

    @Test
    fun testNetworkParser() {
        val appContext = InstrumentationRegistry.getTargetContext()
        val networkService = FakeRestaurantNetworkService(appContext)
        networkService.getRestaurantDetails("RG8")
                .doOnError { " Error: ${it.message}" }
                .subscribe {
                    Assert.assertEquals(it.Restaurants.size, 35)
                    Assert.assertEquals(it.Restaurants[0].Name, "Herbies Pizza")
                    Assert.assertEquals(it.Restaurants[0].Address, "18 Hildens Drive")
                    Assert.assertThat(it.Restaurants[0].RatingAverage.toBigDecimal(), Matchers.comparesEqualTo(BigDecimal("5.07")))

                    Assert.assertEquals(it.Restaurants[0].CuisineTypes.size, 2)
                    Assert.assertEquals(it.Restaurants[0].CuisineTypes[0].Name, "Italian")
                    Assert.assertEquals(it.Restaurants[0].CuisineTypes[1].Name, "Pizza")
                }
    }
}
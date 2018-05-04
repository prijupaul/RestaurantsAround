package nearme.restaurant.com.restaurantsnearme.ui.presenters

import android.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import nearme.restaurant.com.restaurantsnearme.AndroidTest
import nearme.restaurant.com.restaurantsnearme.model.RestaurantDetailsModel
import nearme.restaurant.com.restaurantsnearme.network.FakeRestaurantNetworkService
import nearme.restaurant.com.restaurantsnearme.ui.viewmodel.RestaurantViewModel
import nearme.restaurant.com.restaurantsnearme.utils.RxUtil
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.junit.*
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import java.io.IOException
import java.nio.charset.StandardCharsets

class RestaurantsPresenterTest : AndroidTest() {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var rule = MockitoJUnit.rule()

    private lateinit var mockWebServer: MockWebServer

    @Mock
    private lateinit var ui: RestaurantsPresenter.Ui

    @Mock
    private lateinit var restaurantViewModel: RestaurantViewModel

    @Mock
    private lateinit var rxUtil: RxUtil

    private var networkService = FakeRestaurantNetworkService(context())

    private lateinit var restaurantsPresenter: RestaurantsPresenter

    companion object {
        @BeforeClass
        @JvmStatic
        fun setupBeforeClass() {
            RxAndroidPlugins.reset()
            RxJavaPlugins.reset()
            RxJavaPlugins.setIoSchedulerHandler {
                Schedulers.trampoline()
            }

            RxAndroidPlugins.setInitMainThreadSchedulerHandler {
                Schedulers.trampoline()
            }
        }

        @AfterClass
        @JvmStatic
        fun afterClass() {
            RxAndroidPlugins.reset()
            RxJavaPlugins.reset()
        }
    }


    @Before
    fun setup() {

        MockitoAnnotations.initMocks(this)

        ui = Mockito.mock(RestaurantsPresenter.Ui::class.java)
        restaurantViewModel = Mockito.mock(RestaurantViewModel::class.java)
        rxUtil = Mockito.mock(RxUtil::class.java)

        restaurantsPresenter = RestaurantsPresenter(ui, viewModel = restaurantViewModel,
                networkService = networkService)
    }

    @After
    fun stopService() {
        networkService.triggerError = false
    }


    @Test
    fun `test callback is invoked when viewModel is available`() {
        val mockRestaurantDetails = mock(RestaurantDetailsModel.Restaurants::class.java)
        `when`(restaurantViewModel.restaurantsDetails)
                .thenReturn(mockRestaurantDetails)
        restaurantsPresenter.fetchRestaurants("postcode")
        verify(ui, times(1)).onRestaurantFetched(mockRestaurantDetails.Restaurants)
    }

    @Test
    fun `test progress is invoked when loading from network`() {
        `when`(restaurantViewModel.restaurantsDetails)
                .thenReturn(null)
        restaurantsPresenter.fetchRestaurants("postcode")
        verify(ui, times(1)).onProgress(true)
        verify(ui, times(1)).onProgress(false)
    }

    @Test
    fun `test view complete callback is invoked`() {
        `when`(restaurantViewModel.restaurantsDetails)
                .thenReturn(null)
        restaurantsPresenter.fetchRestaurants("postcode")
        verify(ui, times(1)).onRestaurantFetched(ArgumentMatchers.anyList())
    }

    @Test
    fun `test view error callback is invoked`() {
        `when`(restaurantViewModel.restaurantsDetails)
                .thenReturn(null)
        networkService.triggerError = true
        restaurantsPresenter.fetchRestaurants("postcode")
        verify(ui, times(1)).onError(ArgumentMatchers.anyString())
    }

    @Throws(IOException::class)
    private fun enqueueResponse(fileName: String) {
        enqueueResponse(fileName, emptyMap())
    }

    @Throws(IOException::class)
    private fun enqueueResponse(fileName: String, headers: Map<String, String>) {
        val inputStream = javaClass.classLoader
                .getResourceAsStream("api-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(mockResponse
                .setBody(source.readString(StandardCharsets.UTF_8)))
    }
}
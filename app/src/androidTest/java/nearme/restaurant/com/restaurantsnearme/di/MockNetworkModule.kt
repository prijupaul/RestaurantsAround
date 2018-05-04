package nearme.restaurant.com.restaurantsnearme.di

import android.content.Context
import dagger.Module
import dagger.Provides
import nearme.restaurant.com.restaurantsnearme.network.AbstractRestaurantNetworkService
import nearme.restaurant.com.restaurantsnearme.network.FakeRestaurantNetworkService

import javax.inject.Singleton

@Module
class MockNetworkModule {
    @Singleton
    @Provides
    fun provideNetworkModule(context: Context): AbstractRestaurantNetworkService = FakeRestaurantNetworkService(context)
}
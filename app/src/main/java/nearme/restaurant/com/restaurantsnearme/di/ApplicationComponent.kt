package nearme.restaurant.com.restaurantsnearme.di

import dagger.Component
import dagger.android.AndroidInjector
import nearme.restaurant.com.restaurantsnearme.RestaurantsApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [(ApplicationModule::class)])
interface ApplicationComponent : AndroidInjector<RestaurantsApplication> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<RestaurantsApplication>()
}
package nearme.restaurant.com.restaurantsnearme.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import nearme.restaurant.com.restaurantsnearme.ui.fragments.RestaurantsFragment

@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector
    internal abstract fun restaurantFragmentInjector(): RestaurantsFragment

}
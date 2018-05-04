package nearme.restaurant.com.restaurantsnearme.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import nearme.restaurant.com.restaurantsnearme.RestaurantsApplication
import nearme.restaurant.com.restaurantsnearme.ui.activities.MainActivity
import javax.inject.Singleton

@Module(includes = [(AndroidSupportInjectionModule::class), (MockNetworkModule::class), (MainActivityModule::class)])

abstract class ApplicationModule {
    @Binds
    @Singleton
    internal abstract fun provideApplication(app: RestaurantsApplication): Application

    @Binds
    @Singleton
    internal abstract fun provideContext(application: Application): Context

    @ContributesAndroidInjector(modules = [(MainActivityModule::class)])
    internal abstract fun mainActivity(): MainActivity

}
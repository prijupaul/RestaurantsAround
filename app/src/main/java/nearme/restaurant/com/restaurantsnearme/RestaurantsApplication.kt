package nearme.restaurant.com.restaurantsnearme

import android.app.Activity
import android.app.Application
import android.support.v4.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import nearme.restaurant.com.restaurantsnearme.di.DaggerApplicationComponent
import javax.inject.Inject

class RestaurantsApplication: Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var supportFragmentAndroidInjector: DispatchingAndroidInjector<Fragment>


    override fun onCreate() {
        super.onCreate()

        DaggerApplicationComponent
                .builder()
                .create(this)
                .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingAndroidInjector

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return supportFragmentAndroidInjector
    }

}
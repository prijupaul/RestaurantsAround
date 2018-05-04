package nearme.restaurant.com.restaurantsnearme.ui.activities

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.toolbar_layout.*
import nearme.restaurant.com.restaurantsnearme.R
import nearme.restaurant.com.restaurantsnearme.network.AbstractRestaurantNetworkService
import nearme.restaurant.com.restaurantsnearme.ui.fragments.RestaurantsFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var networkService: AbstractRestaurantNetworkService

    lateinit var appName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        appName = getString(R.string.app_name)
        setContentView(R.layout.activity_main)
        val toolbar = toolbar
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            title = getString(R.string.app_name)
            setDisplayShowTitleEnabled(false)
        }

        transitionTransaction(RestaurantsFragment(), RestaurantsFragment::class.simpleName).commit()
    }

    private fun transitionTransaction(fragment: Fragment, tag: String?): FragmentTransaction {
        return transitionTransaction(fragment, tag, false)
    }

    private fun transitionTransaction(fragment: Fragment, tag: String?, popBefore: Boolean): FragmentTransaction {
        val fm = supportFragmentManager
        if (popBefore && fm.backStackEntryCount > 0) {
            fm.popBackStack()
        }
        return fm.beginTransaction().add(R.id.container, fragment, tag)
    }

    fun clearBackStack() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}
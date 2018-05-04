package nearme.restaurant.com.restaurantsnearme.ui.fragments

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import junit.framework.Assert.assertNotNull
import kotlinx.android.synthetic.main.toolbar_layout.*
import nearme.restaurant.com.restaurantsnearme.BuildConfig
import nearme.restaurant.com.restaurantsnearme.R
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil
import kotlin.test.assertEquals


@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class,
        sdk = intArrayOf(21))

class RestaurantsFragmentTest {

    @Test
    @Throws(Exception::class)
    fun `test fragment creation`() {
        val fragment = RestaurantsFragment()
        assertNotNull(fragment)
    }

    @Test
    fun `test fragment toolbar title`() {

        val fragment = RestaurantsFragment()
        SupportFragmentTestUtil.startFragment(fragment, AppCompatActivity::class.java)
        assertEquals("RestaurantsNearMe",fragment?.activity?.toolbar?.title)
    }

    @Test
    fun `test recyclerview loaded`() {
        val fragment = RestaurantsFragment()
        SupportFragmentTestUtil.startFragment(fragment, AppCompatActivity::class.java)
        val recyclerView = fragment.view?.findViewById<RecyclerView>(R.id.rec_view)
        assertEquals(true, recyclerView?.visibility == View.VISIBLE)
    }
}
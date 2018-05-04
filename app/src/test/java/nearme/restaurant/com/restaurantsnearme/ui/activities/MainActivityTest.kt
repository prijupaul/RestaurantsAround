package nearme.restaurant.com.restaurantsnearme.ui.activities

import kotlinx.android.synthetic.main.toolbar_layout.*
import nearme.restaurant.com.restaurantsnearme.BuildConfig
import nearme.restaurant.com.restaurantsnearme.R
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class,
        sdk = intArrayOf(21))

class MainActivityTest {

    private lateinit var activity: MainActivity

    @Before
    fun setup() {

        activity = Robolectric.setupActivity(MainActivity::class.java)
    }

    @Test
    fun `activity should not be null`() {
        assertNotNull(activity)
        assertNotNull(activity.context)
        assertNotNull(activity.appName)
        System.out.println(activity.appName)
        assertNotNull(activity.networkService)
    }

    @Test
    fun `test toolbar name`() {
        assertEquals(activity.toolbar.title, activity.getString(R.string.app_name))
    }

    @Test
    fun `activity should have restaurants fragment`() {
        assertEquals(activity.fragmentManager.fragments.size, 1)
        assertNotNull(activity.fragmentManager.fragments[0].tag)
    }
}
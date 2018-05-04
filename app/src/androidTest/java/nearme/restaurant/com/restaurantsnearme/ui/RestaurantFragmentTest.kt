package nearme.restaurant.com.restaurantsnearme.ui

import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingPolicies
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.RootMatchers.withDecorView
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.TextView
import nearme.restaurant.com.restaurantsnearme.R
import nearme.restaurant.com.restaurantsnearme.ui.activities.MainActivity
import nearme.restaurant.com.restaurantsnearme.ui.fragments.RestaurantsFragment
import nearme.restaurant.com.restaurantsnearme.util.EspressoTestUtil
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
@LargeTest

class RestaurantFragmentTest {
    @Rule
    @JvmField
    var mActivityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun init() {
        EspressoTestUtil.disableProgressBarAnimations(mActivityRule)

        mActivityRule.activity.runOnUiThread {
            val fragmentTransaction = mActivityRule.activity
                    .supportFragmentManager.beginTransaction()
            val restaurantFragment = RestaurantsFragment()
            fragmentTransaction.replace(R.id.container, restaurantFragment, "restaurantFrag")
            fragmentTransaction.commitAllowingStateLoss()
        }
    }

    @After
    fun tearDown() {

    }

    @Test
    fun ensureToolbarTitle() {
        val toolbarTitle = getInstrumentation().targetContext.getString(R.string.app_name)
        onView(allOf(isAssignableFrom(TextView::class.java), withParent(isAssignableFrom(Toolbar::class.java))))
                .check(matches(withText(toolbarTitle)))
    }

    @Test
    fun restaurantFragIsLoaded() {
        onView(withId(R.id.restaurantfrag_root))
                .check(matches(isDisplayed()))
    }

    @Test
    fun loadRecyclerViewTest() {

        IdlingPolicies.setMasterPolicyTimeout(3000 * 2, TimeUnit.MILLISECONDS)
        IdlingPolicies.setIdlingResourceTimeout(3000 * 2, TimeUnit.MILLISECONDS)
        val idlingResource = ElapsedTimeIdlingResource(3000L)
        IdlingRegistry.getInstance().register(idlingResource)

        onView(withId(R.id.rec_view)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click()))
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    @Test
    fun recyclerViewClickTest() {
        IdlingPolicies.setMasterPolicyTimeout(3000 * 2, TimeUnit.MILLISECONDS)
        IdlingPolicies.setIdlingResourceTimeout(3000 * 2, TimeUnit.MILLISECONDS)
        val idlingResource = ElapsedTimeIdlingResource(3000L)
        IdlingRegistry.getInstance().register(idlingResource)

        onView(withId(R.id.rec_view)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click()))
        onView(withText(R.string.toast)).inRoot(withDecorView(not(mActivityRule.activity.window.decorView))).check(matches(isDisplayed()))

        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    private fun getRVcount(): Int {
        val recyclerView = mActivityRule.activity.findViewById(R.id.rec_view) as RecyclerView
        Log.d("Priju", "recyclerView: ${recyclerView.adapter.itemCount}")
        return recyclerView.adapter.itemCount
    }
}
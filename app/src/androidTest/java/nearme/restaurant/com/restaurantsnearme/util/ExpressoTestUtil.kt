package nearme.restaurant.com.restaurantsnearme.util

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.test.rule.ActivityTestRule
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar


object EspressoTestUtil {
    /**
     * Disables progress bar animations for the views of the given activity rule
     *
     * @param activityTestRule The activity rule whose views will be checked
     */
    fun disableProgressBarAnimations(
            activityTestRule: ActivityTestRule<out FragmentActivity>) {
        activityTestRule.activity.supportFragmentManager
                .registerFragmentLifecycleCallbacks(
                        object : FragmentManager.FragmentLifecycleCallbacks() {
                            override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View,
                                                               savedInstanceState: Bundle) {
                                // traverse all views, if any is a progress bar, replace its animation
                                traverseViews(v)
                            }
                        }, true)
    }

    private fun traverseViews(view: View) {
        if (view is ViewGroup) {
            traverseViewGroup(view as ViewGroup)
        } else {
            if (view is ProgressBar) {
                disableProgressBarAnimation(view as ProgressBar)
            }
        }
    }

    private fun traverseViewGroup(view: ViewGroup) {
        val count = view.childCount
        for (i in 0 until count) {
            traverseViews(view.getChildAt(i))
        }
    }

    /**
     * necessary to run tests on older API levels where progress bar uses handler loop to animate.
     *
     * @param progressBar The progress bar whose animation will be swapped with a drawable
     */
    private fun disableProgressBarAnimation(progressBar: ProgressBar) {
        progressBar.indeterminateDrawable = ColorDrawable(Color.BLUE)
    }
}
package nearme.restaurant.com.restaurantsnearme.ui

import android.support.test.espresso.IdlingResource

class ElapsedTimeIdlingResource(private val waitingTime: Long) : IdlingResource {

    var startTime = System.currentTimeMillis()
    lateinit var resourceCallback: IdlingResource.ResourceCallback

    override fun getName(): String = ElapsedTimeIdlingResource::class.java.simpleName

    override fun isIdleNow(): Boolean {
        val elapsedTime = System.currentTimeMillis() - startTime
        val idle = elapsedTime >= waitingTime
        if(idle){
            resourceCallback.onTransitionToIdle()
        }
        return idle
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        callback?.run { resourceCallback = this }
    }
}
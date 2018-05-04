package nearme.restaurant.com.restaurantsnearme

import android.app.Application
import android.content.Context
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * Base class for Robolectric data layer tests.
 * Inherit from this class to create a test.
 */
@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class,
        application = AndroidTest.ApplicationStub::class,
        sdk = intArrayOf(21))

abstract class AndroidTest {

    fun context(): Context {
        return RuntimeEnvironment.application
    }

    internal class ApplicationStub : Application()
}

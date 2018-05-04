package nearme.restaurant.com.restaurantsnearme.ui.fragments

import android.content.Context
import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import dagger.android.support.AndroidSupportInjection
import nearme.restaurant.com.restaurantsnearme.R
import nearme.restaurant.com.restaurantsnearme.ui.presenters.Presenter


abstract class BaseFragment<out PRES : Presenter<Presenter.Ui>> : Fragment(), Presenter.Ui {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private lateinit var mPresenter: PRES
    protected val presenter: PRES
        get() = mPresenter

    private var mProgressBar: ProgressBar? = null

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        mPresenter = createPresenter()
    }

    protected fun createView(layoutResID: Int, inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(layoutResID, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null)
            onInitialSetup()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter?.clearPresenter()
    }

    open fun onInitialSetup() {
    }

    override fun onProgress(loading: Boolean) {
        if (mProgressBar?.isShown == true) {
            mProgressBar?.visibility = View.GONE
            mProgressBar = null
        }

        if (loading) {
            mProgressBar = activity?.findViewById(R.id.progressBar)
            mProgressBar?.visibility = View.VISIBLE
        }
    }

    fun transitionFragmentTransaction(fragment: Fragment, tag: String?, pushToBackStack: Boolean): FragmentTransaction? {
        val fm = fragmentManager
        val fragmentTransaction = fm?.beginTransaction()
        fragmentTransaction?.replace(R.id.container, fragment, tag)
        if (pushToBackStack) {
            fragmentTransaction?.addToBackStack(tag)
        }
        return fragmentTransaction
    }

    override fun onBack() {
        when (fragmentManager?.backStackEntryCount == 0) {
            true -> activity?.finish()
            else -> childFragmentManager.popBackStack()
        }
    }

    override fun onError(error: String) {
        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
    }

    protected abstract fun createPresenter(): PRES
}
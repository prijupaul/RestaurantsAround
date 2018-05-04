package nearme.restaurant.com.restaurantsnearme.ui.presenters

import android.arch.lifecycle.DefaultLifecycleObserver
import android.arch.lifecycle.ProcessLifecycleOwner
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<out UI : Presenter.Ui> protected constructor(ui: UI) : Presenter<UI>(ui), DefaultLifecycleObserver {
    interface Ui : Presenter.Ui

    protected val mCompositeDisposable = CompositeDisposable()

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun clearPresenter() {
        mCompositeDisposable.dispose()
    }
}
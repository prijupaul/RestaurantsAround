package nearme.restaurant.com.restaurantsnearme.ui.presenters

abstract class Presenter<out UI : Presenter.Ui> protected constructor(val ui: UI) {

    abstract fun clearPresenter()

    interface Ui {

        fun onBack()

        fun onError(error: String)

        fun onProgress(loading: Boolean)
    }


}
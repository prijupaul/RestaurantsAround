package nearme.restaurant.com.restaurantsnearme.ui.presenters

import io.reactivex.disposables.Disposable
import nearme.restaurant.com.restaurantsnearme.model.RestaurantDetailsModel
import nearme.restaurant.com.restaurantsnearme.network.AbstractRestaurantNetworkService
import nearme.restaurant.com.restaurantsnearme.ui.viewmodel.RestaurantViewModel
import nearme.restaurant.com.restaurantsnearme.utils.RxUtil
import javax.inject.Inject

class RestaurantsPresenter @Inject constructor(ui: Ui,
                                               private val networkService: AbstractRestaurantNetworkService,
                                               private val viewModel: RestaurantViewModel) : BasePresenter<RestaurantsPresenter.Ui>(ui) {

    fun fetchRestaurants(postCode: String) {

        when (viewModel.restaurantsDetails) {
            null -> fetchFromNetwork(postCode)
            else -> ui?.onRestaurantFetched(viewModel.restaurantsDetails!!.Restaurants)
        }
    }

    private fun fetchFromNetwork(postCode: String) {
        var dispose: Disposable? = null
        dispose = networkService.getRestaurantDetails(postCode)
                .compose(RxUtil().uiScheduler())
                .doOnSubscribe { ui.onProgress(true) }
                .doFinally { ui.onProgress(false) }
                .doOnNext { result -> viewModel.restaurantsDetails = result }
                .subscribe(
                        { result ->
                            dispose?.run { mCompositeDisposable.remove(this) }
                            ui?.onRestaurantFetched(result.Restaurants)
                        },
                        { error ->
                            error.message?.run { ui?.onError(this) }
                        })
        mCompositeDisposable.add(dispose)
    }

    interface Ui : BasePresenter.Ui {
        fun onRestaurantFetched(restaurantDetails: List<RestaurantDetailsModel.RestaurantsDetails>)
    }
}
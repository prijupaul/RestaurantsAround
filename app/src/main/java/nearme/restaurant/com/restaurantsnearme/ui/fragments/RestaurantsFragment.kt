package nearme.restaurant.com.restaurantsnearme.ui.fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.restaurants_list.*
import nearme.restaurant.com.restaurantsnearme.R
import nearme.restaurant.com.restaurantsnearme.model.RestaurantDetailsModel
import nearme.restaurant.com.restaurantsnearme.network.AbstractRestaurantNetworkService
import nearme.restaurant.com.restaurantsnearme.ui.adapters.RestaurantsAdapter
import nearme.restaurant.com.restaurantsnearme.ui.presenters.RestaurantsPresenter
import nearme.restaurant.com.restaurantsnearme.ui.viewmodel.RestaurantViewModel
import javax.inject.Inject

class RestaurantsFragment : BaseFragment<RestaurantsPresenter>(), RestaurantsPresenter.Ui {

    @Inject
    lateinit var networkService: AbstractRestaurantNetworkService

    lateinit var adapter: RestaurantsAdapter

    private val restaurantsViewModel: RestaurantViewModel by lazy {
        ViewModelProviders.of(this.activity as AppCompatActivity).get(RestaurantViewModel::class.java)
    }


    override fun createPresenter() = RestaurantsPresenter(this, networkService,restaurantsViewModel)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.apply {
            title = getString(R.string.app_name)
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowTitleEnabled(true)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(R.layout.restaurants_list, inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RestaurantsAdapter(context!!)
        rec_view.adapter = adapter
        rec_view.layoutManager = LinearLayoutManager(this.activity?.applicationContext)

        presenter.fetchRestaurants("SE19")
    }

    override fun onRestaurantFetched(restaurantDetails: List<RestaurantDetailsModel.RestaurantsDetails>) {
        adapter.replaceAll(restaurantDetails)
        adapter.notifyDataSetChanged()
    }
}
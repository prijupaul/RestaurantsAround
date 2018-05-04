package nearme.restaurant.com.restaurantsnearme.ui.adapters

import android.content.Context
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_rest.view.*
import nearme.restaurant.com.restaurantsnearme.R
import nearme.restaurant.com.restaurantsnearme.model.RestaurantDetailsModel

class RestaurantsAdapter(val context: Context): BaseRecyclerAdapter<Any>(context, R.layout.list_item_rest) {

    override fun bindData(view: View, item: Any?, position: Int) {
        view.apply {
            item as RestaurantDetailsModel.RestaurantsDetails
            tv_name.text = item.Name
            tv_address.text = item.Address

            Picasso.get()
                    .load(item.Logo[0].StandardResolutionURL)
                    .into(img_logo)

            ratingBar.rating = item.RatingAverage
            val cuisines = StringBuffer()
            cuisines.append(item.CuisineTypes.first().Name)
            item.CuisineTypes.subList(1,item.CuisineTypes.size)
                    .forEach { cuisines.append(", ${it.Name}") }

            tv_cuisines.text = cuisines
        }
    }
}
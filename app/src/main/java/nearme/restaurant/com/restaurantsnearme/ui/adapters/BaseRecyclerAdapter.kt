package nearme.restaurant.com.restaurantsnearme.ui.adapters

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseRecyclerAdapter<T>(context: Context, @LayoutRes val layoutRes: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onItemClickListener: ((T?, Int) -> Unit)? = null
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private val mItems = arrayListOf<T>()

    override fun getItemCount(): Int {
        return mItems.size
    }

    fun addAll(items: List<T>) {
        mItems.addAll(items)
    }

    fun replaceAll(items: List<T>) {
        clear()
        if (!items.isEmpty()) {
            addAll(items)
        }
    }

    fun clear() {
        mItems.clear()
    }

    open fun createView(parent: ViewGroup?, viewType: Int): View {
        return mInflater.inflate(layoutRes, parent, false).apply {
            isClickable = true
            isEnabled = true
        }
    }

    abstract fun bindData(view: View, item: T?, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(createView(parent, viewType)) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        setOnClickListener(holder, item, position)
        bindData(holder.itemView, item, position)
    }

    protected open fun setOnClickListener(holder: RecyclerView.ViewHolder, item: T?, position: Int) {
        holder.itemView.setOnClickListener { onItemClickListener?.invoke(item, mItems.indexOf(item)) }
    }

    protected open fun getItem(position: Int): T? {
        return mItems[position]
    }

    open fun insertedItem(index: Int, item: T) {
        mItems.add(index, item)
    }

    fun removeItem(index: Int) {
        mItems.removeAt(index)
    }
}
package com.zshock.weatherchecker.ui.search

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zshock.weatherchecker.R
import com.zshock.weatherchecker.domain.model.City
import com.zshock.weatherchecker.helper.ColorHelper
import kotlinx.android.synthetic.main.item_header.view.*
import kotlinx.android.synthetic.main.item_recent_city.view.*

class CityRecentAdapter(var callback: RecentCityViewHolder.Callback) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: MutableList<City>? = null

    val TYPE_HEADER = 0
    val TYPE_CITY = 1

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            TYPE_HEADER
        } else {
            TYPE_CITY
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_HEADER) {
            HeaderViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(HeaderViewHolder.layoutId, parent, false)
            )
        } else {
            RecentCityViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(RecentCityViewHolder.layoutId, parent, false)
            )
        }
    }

    override fun getItemCount(): Int {
        if (items.isNullOrEmpty()) {
            return 0
        } else {
            return items?.size?.plus(1) ?: 0
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_HEADER) {
            (holder as? HeaderViewHolder)?.setTitle(R.string.search_recent)
        } else {
            items?.get(position - 1)
                ?.let { (holder as? RecentCityViewHolder)?.setCity(it, callback) }
        }
    }

    fun setItems(response: List<City>) {
        items = response.toMutableList()
        notifyDataSetChanged()
    }

    fun removeCity(cityId: Long) {
        val index = items?.indexOf(items?.find { it.id == cityId })
        index?.let {
            items?.removeAt(index)
            notifyDataSetChanged()
        }
    }

    fun addCity(city: City) {
        items?.let {
            it.add(0, city)
            notifyItemInserted(1)
            while (it.size > 5) {
                it.removeAt(5)
                notifyItemRemoved(it.size)
            }
        }
    }

    class RecentCityViewHolder(view: View) : CityAdapter.CityViewHolder(view) {

        companion object {
            const val layoutId = R.layout.item_recent_city
        }

        fun setCity(city: City, callback: Callback) {
            view.countryCodeTextView.text = city.countryCode
            view.countryCodeTextView.background.colorFilter = BlendModeColorFilter(
                ColorHelper.getMaterialColor(city.id, view.context), BlendMode.SRC_ATOP
            )
            view.nameTextView.text = city.name
            view.setOnClickListener { callback.onRecentCityClicked(city) }
            view.deleteButton.setOnClickListener { callback.onDeleteRecentCityClicked(city) }
        }

        interface Callback : CityAdapter.CityViewHolder.Callback {
            fun onDeleteRecentCityClicked(city: City)
            fun onRecentCityClicked(city: City)
        }

    }

    class HeaderViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        companion object {
            const val layoutId = R.layout.item_header
        }

        fun setTitle(stringResId: Int) {
            view.titleTextView.setText(stringResId)
        }
    }
}

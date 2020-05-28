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
import kotlinx.android.synthetic.main.item_city.view.*

class CityAdapter(var callback: CityViewHolder.Callback?) :
    RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    private var items: MutableList<City>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder(
            LayoutInflater.from(parent.context).inflate(CityViewHolder.layoutId, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        items?.get(position)?.let {
            holder.setCity(it, callback)
        }
    }

    fun setItems(response: List<City>) {
        items = response.toMutableList()
        notifyDataSetChanged()
    }

    open class CityViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        companion object {
            const val layoutId = R.layout.item_city
        }

        fun setCity(city: City, callback: Callback?) {
            view.countryCodeTextView.text = city.countryCode
            view.countryCodeTextView.background.colorFilter = BlendModeColorFilter(
                ColorHelper.getMaterialColor(city.id, view.context), BlendMode.SRC_ATOP
            )
            view.nameTextView.text = city.name
            view.setOnClickListener { callback?.onCityClicked(city) }
        }

        interface Callback {
            fun onCityClicked(city: City)
        }

    }
}
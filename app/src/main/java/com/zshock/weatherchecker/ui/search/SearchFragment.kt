package com.zshock.weatherchecker.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.zshock.weatherchecker.R
import com.zshock.weatherchecker.domain.model.City
import com.zshock.weatherchecker.ui.base.BaseFragment
import com.zshock.weatherchecker.ui.detail.CityWeatherDetailFragment
import com.zshock.weatherchecker.ui.base.ParentNavigation
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : BaseFragment(), SearchContract.View, CityAdapter.CityViewHolder.Callback,
    CityRecentAdapter.RecentCityViewHolder.Callback {

    lateinit var presenter: SearchContract.Presenter
    var cityResultsAdapter: CityAdapter? = null
    var cityRecentAdapter: CityRecentAdapter? = null

    override val layoutResId: Int
        get() = R.layout.fragment_search

    companion object {
        const val TAG: String = "fragment_search"

        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = SearchPresenter()
        presenter.setView(this)
        searchEditText.addTextChangedListener(searchTextWatcher)
        if (savedInstanceState == null) {
            presenter.getRecent()
        }
    }

    private val searchTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun afterTextChanged(s: Editable?) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s.isNullOrEmpty()) {
                presenter.getRecent()
            } else {
                presenter.searchFor(s.toString())
            }
        }
    }

    // Presenter Reponses
    override fun onCitySearchResponse(response: List<City>) {
        if (cityResultsAdapter == null) {
            cityResultsAdapter = CityAdapter(this)
            resultsRecyclerView.adapter = cityResultsAdapter
        }
        cityResultsAdapter?.setItems(response)
        recentRecyclerView.visibility = View.GONE
        resultsRecyclerView.visibility = View.VISIBLE
    }

    override fun onRecentCitiesLoaded(response: List<City>) {
        if (cityRecentAdapter == null) {
            cityRecentAdapter = CityRecentAdapter(this)
            recentRecyclerView.adapter = cityRecentAdapter
        }
        cityRecentAdapter?.setItems(response)
        resultsRecyclerView.visibility = View.GONE
        recentRecyclerView.visibility = View.VISIBLE
    }

    override fun onCityClicked(city: City) {
        hideKeyboard()
        presenter.addCityToRecent(city)
        (activity as? ParentNavigation)?.pushFragment(
            CityWeatherDetailFragment.newInstance(city),
            CityWeatherDetailFragment.TAG
        )
    }

    override fun onRecentCityClicked(city: City) {
        hideKeyboard()
        (activity as? ParentNavigation)?.pushFragment(
            CityWeatherDetailFragment.newInstance(city),
            CityWeatherDetailFragment.TAG
        )
    }

    override fun onDeleteRecentCityClicked(city: City) {
        presenter.removeCityFromRecents(city)
    }

    override fun onCityRemovedFromRecent(cityId: Long) {
        cityRecentAdapter?.removeCity(cityId)
    }

    override fun onCityAddedToRecent(city: City) {
        cityRecentAdapter?.addCity(city)
    }

    // Navigation Stuff
    override fun onBackPressed(): Boolean {
        if (searchEditText.text.isNotBlank()) {
            searchEditText.text = null
            return true
        } else {
            return super.onBackPressed()
        }
    }


    override fun getTitle(): String? {
        return null
    }

}
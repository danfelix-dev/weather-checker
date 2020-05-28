package com.zshock.weatherchecker.ui.settings

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.preference.PreferenceFragmentCompat
import com.zshock.weatherchecker.R
import com.zshock.weatherchecker.ui.base.ChildNavigation


class SettingsFragment : PreferenceFragmentCompat(), ChildNavigation {

    companion object {
        const val TAG = "fragment_settings"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {
            // Hack to get the colorBackground color (for DayNight Material theme)
            val a = TypedValue()
            it.theme?.resolveAttribute(android.R.attr.colorBackground, a, true)
            val color = a.data
            view.setBackgroundColor(color)
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    // Navigation Stuff
    override fun getTitle(): String? {
        return getString(R.string.settings)
    }

    override fun onBackPressed(): Boolean {
        return false
    }

}
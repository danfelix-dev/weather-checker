package com.zshock.weatherchecker.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.libraries.maps.MapView
import com.zshock.weatherchecker.R
import com.zshock.weatherchecker.ui.base.ChildNavigation
import com.zshock.weatherchecker.ui.base.ParentNavigation
import com.zshock.weatherchecker.ui.search.SearchFragment
import com.zshock.weatherchecker.ui.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener,
    ParentNavigation {

    private var settingsButton: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.addOnBackStackChangedListener(this)
        val searchFragment: Fragment? = supportFragmentManager.findFragmentByTag(SearchFragment.TAG)
        if (searchFragment == null) {
            setRootFragment(SearchFragment.newInstance(), SearchFragment.TAG)
        }

        // Hack to preload maps so it doesn't take 1 second to load the first time
        MainScope().launch {
            val mapView = MapView(this@MainActivity)
            mapView.onCreate(null)
            mapView.getMapAsync {
                it.toString()
            }
        }

        setSupportActionBar(toolbar)
        updateToolbar()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        settingsButton = menu?.findItem(R.id.settings)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.settings -> pushFragment(SettingsFragment(), SettingsFragment.TAG)
            else -> return false
        }
        return true
    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        if (frag is ChildNavigation) {
            val wasBackPressHandled = frag.onBackPressed()
            if (!wasBackPressHandled) {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }
    }

    override fun onBackStackChanged() {
        updateToolbar()
    }

    private fun updateToolbar() {
        val fragments = supportFragmentManager.fragments
        if (fragments.isNotEmpty() && fragments.size > 1) {
            val currentFragment = fragments.last() as? ChildNavigation
            toolbar.title = currentFragment?.getTitle() ?: getString(R.string.app_name)
            settingsButton?.isVisible = currentFragment !is SettingsFragment
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        } else {
            toolbar.title = getString(R.string.app_name)
            settingsButton?.isVisible = true
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun pushFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, fragment, tag)
            .addToBackStack(null)
            .commit()
    }

    override fun setRootFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, fragment, tag)
            .commit()
    }

}
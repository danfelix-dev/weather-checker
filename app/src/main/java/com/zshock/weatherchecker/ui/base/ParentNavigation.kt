package com.zshock.weatherchecker.ui.base

import androidx.fragment.app.Fragment

interface ParentNavigation {
    fun pushFragment(fragment: Fragment, tag: String)
    fun setRootFragment(fragment: Fragment, tag: String)
}

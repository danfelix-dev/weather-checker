package com.zshock.weatherchecker.ui.base

interface ChildNavigation {
    fun getTitle(): String?
    fun onBackPressed(): Boolean
}

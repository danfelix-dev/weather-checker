package com.zshock.weatherchecker.ui.base

interface BasePresenter<T : BaseView?> {
    fun setView(view: T?)
}
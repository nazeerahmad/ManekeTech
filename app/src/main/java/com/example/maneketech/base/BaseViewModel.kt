package com.example.maneketech.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference

open class BaseViewModel<N>():ViewModel() {
    private val mIsLoading = ObservableBoolean()

    private var mNavigator: WeakReference<N>? = null

    fun getIsLoading(): ObservableBoolean? {
        return mIsLoading
    }

    fun setIsLoading(isLoading: Boolean) {
        mIsLoading.set(isLoading)
    }

    fun getNavigator(): N? {
        return mNavigator!!.get()
    }

    fun setNavigator(navigator: N) {
        mNavigator = WeakReference(navigator)
    }


}
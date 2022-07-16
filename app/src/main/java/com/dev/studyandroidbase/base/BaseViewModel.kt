package com.dev.studyandroidbase.base

import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference

abstract class BaseViewModel<N>: ViewModel() {
	private lateinit var mNavigation: WeakReference<N>
	
	fun getNavigator(): N{
		return this.mNavigation.get()!!
	}
	
	fun setNavigator(navigator: N) {
		mNavigation = WeakReference(navigator)
	}
}
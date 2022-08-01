package com.dev.studyandroidbase

import androidx.multidex.MultiDexApplication
import com.dev.studyandroidbase.utils.AppLogger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication: MultiDexApplication() {

	override fun onCreate() {
		super.onCreate()
		_instance = this
		AppLogger.init()
	}
	
	companion object {
		private lateinit var _instance: MainApplication
		val instance: MainApplication
			get() = _instance
	}

}
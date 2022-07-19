package com.dev.studyandroidbase

import androidx.multidex.MultiDexApplication
import com.dev.studyandroidbase.utils.AppLogger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication: MultiDexApplication() {

	override fun onCreate() {
		super.onCreate()
		AppLogger.init()
	}
	
	companion object {
		private lateinit var instance: MainApplication
		val getInstance: MainApplication
			get() {
				return instance
			}
	}

}
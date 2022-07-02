package com.dev.studyandroidbase

import android.app.Application
import androidx.viewbinding.BuildConfig
import androidx.work.Configuration
import com.dev.studyandroidbase.utils.AppLogger

class MainApplication: Application(), Configuration.Provider {

	override fun onCreate() {
		super.onCreate()
		AppLogger.init()
	}

	override fun getWorkManagerConfiguration(): Configuration =
		if (BuildConfig.DEBUG) Configuration.Builder()
			.setMinimumLoggingLevel(android.util.Log.DEBUG)
			.build()
		else Configuration.Builder()
			.setMinimumLoggingLevel(android.util.Log.ERROR)
			.build()
}
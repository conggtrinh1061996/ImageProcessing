package com.dev.studyandroidbase

import android.app.Application
import androidx.viewbinding.BuildConfig
import androidx.work.Configuration
import com.dev.studyandroidbase.utils.AppLogger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication: Application() {

	override fun onCreate() {
		super.onCreate()
		AppLogger.init()
	}

}
package com.dev.studyandroidbase

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.multidex.MultiDexApplication
import com.dev.studyandroidbase.data.local.prefs.PreferenceHelper
import com.dev.studyandroidbase.notification.NotificationSchedule
import com.dev.studyandroidbase.utils.AppLogger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication: MultiDexApplication() {

	lateinit var displayMetrics: DisplayMetrics

	override fun onCreate() {
		super.onCreate()
		_instance = this
		AppLogger.init()
		displayMetrics = getScreen(this)
		if (PreferenceHelper.getInstance().isFirstOpenApp) {
			PreferenceHelper.getInstance().isFirstOpenApp = false
			NotificationSchedule.createNotificationChannel(this)
		}
	}
	
	companion object {
		private lateinit var _instance: MainApplication
		val instance: MainApplication
			get() = _instance
	}

	private fun getScreen(context: Context): DisplayMetrics {
		val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
		val dm = DisplayMetrics()
		windowManager.defaultDisplay.getRealMetrics(dm)
		return dm
	}

}
package com.dev.studyandroidbase.utils

import androidx.viewbinding.BuildConfig
import timber.log.Timber


object AppLogger {
	val TAG = "AndroidBase"

	fun d(s: String?, vararg objects: Any?) {
		Timber.tag(TAG).d(s, *objects)
	}

	fun init() {
		if (BuildConfig.DEBUG) {
			Timber.plant(Timber.asTree())
		}
	}
}
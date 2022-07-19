package com.dev.studyandroidbase.utils

import com.dev.studyandroidbase.BuildConfig
import timber.log.Timber
import timber.log.Timber.DebugTree


object AppLogger {
	val TAG = "AndroidBase"
	
	fun d(s: String?, vararg objects: Any?) {
		Timber.tag(TAG).d(s, *objects)
	}
	
	fun d(throwable: Throwable?, s: String?, vararg objects: Any?) {
		Timber.tag(TAG).d(throwable, s, *objects)
	}
	
	fun e(s: String?, vararg objects: Any?) {
		Timber.tag(TAG).e(s, *objects)
	}
	
	fun e(throwable: Throwable?, s: String?, vararg objects: Any?) {
		Timber.tag(TAG).e(throwable, s, *objects)
	}

	fun init() {
		if (BuildConfig.DEBUG) {
			Timber.plant(DebugTree())
		}
	}
}
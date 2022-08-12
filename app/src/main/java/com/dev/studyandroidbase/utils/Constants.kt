package com.dev.studyandroidbase.utils

object Constants {
	
	const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/"
	
	const val BASE_URL_FIRST = "https://android-kotlin-fun-mars-server.appspot.com/realestate"
	const val BASE_URL_SECOND = "https://android-kotlin-fun-mars-server.appspot.com/photos"
	
	const val PREF_NAME = "RenderEffect_pref"
	
	object FilterType {
		const val NO_FILTER = 0
		const val RED = 1
		const val GREEN = 2
		const val BLUE = 3
		const val GRAY = 4
		const val REVERSE = 5
	}
	
	object permissions {
		const val READ_EXTERNAL_STORAGE = android.Manifest.permission.READ_EXTERNAL_STORAGE
		const val WRITE_EXTERNAL_STORAGE = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
	}
}
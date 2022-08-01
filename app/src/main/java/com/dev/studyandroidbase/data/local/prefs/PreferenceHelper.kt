package com.dev.studyandroidbase.data.local.prefs

import android.content.Context
import com.dev.studyandroidbase.MainApplication
import com.dev.studyandroidbase.utils.Constants.PREF_NAME
import javax.inject.Inject

class PreferenceHelper @Inject constructor(
	context: Context
): Preference(context, PREF_NAME) {
	
	var uriMainImage by stringPref("uri_main_image", "")
	
	companion object {
		private var instance: PreferenceHelper? = null
		
		fun getInstance(): PreferenceHelper = instance ?: PreferenceHelper(MainApplication.instance)
	}
}
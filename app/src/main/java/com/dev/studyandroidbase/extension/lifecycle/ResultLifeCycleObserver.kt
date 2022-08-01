package com.dev.studyandroidbase.extension.lifecycle

import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.dev.studyandroidbase.data.local.prefs.PreferenceHelper

class ResultLifeCycleObserver(
	private val registry: ActivityResultRegistry
): DefaultLifecycleObserver {
	
	lateinit var getContent: ActivityResultLauncher<String>
	var uri: Uri? = null
	
	override fun onCreate(owner: LifecycleOwner) {
		getContent = registry.register("key", owner, GetContent()) { uri ->
			PreferenceHelper.getInstance().uriMainImage = uri.toString()
			this.uri = uri
		}
	}
	
	fun selectImage() {
		getContent.launch("image/*")
	}
}
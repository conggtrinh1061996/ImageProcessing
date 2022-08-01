package com.dev.studyandroidbase.extension.lifecycle

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class ResultLifeCycleObserver(
	private val registry: ActivityResultRegistry
): DefaultLifecycleObserver {
	
	lateinit var getContent: ActivityResultLauncher<String>
	
	override fun onCreate(owner: LifecycleOwner) {
		getContent = registry.register("key", owner, GetContent()) { uri ->
			//Handle the return uri
		}
	}
	
	fun selectImage() {
		getContent.launch("image/*")
	}
}
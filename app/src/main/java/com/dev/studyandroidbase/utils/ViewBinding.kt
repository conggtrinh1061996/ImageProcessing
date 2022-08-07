package com.dev.studyandroidbase.utils

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

object ViewBinding {
	
	@BindingAdapter("loadingProgressBar")
	@JvmStatic
	fun loadingProgressBar(view: View, isLoading: Boolean) {
		view.isVisible = isLoading
	}
}
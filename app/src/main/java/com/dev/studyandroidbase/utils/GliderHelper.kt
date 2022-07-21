package com.dev.studyandroidbase.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

object GliderHelper {
	
	fun loadImage(image: ImageView, url: String) {
		Glide.with(image)
			.load(url)
			.into(image)
	}
}
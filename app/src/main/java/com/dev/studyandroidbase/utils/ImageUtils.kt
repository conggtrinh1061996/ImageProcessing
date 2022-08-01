package com.dev.studyandroidbase.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.dev.studyandroidbase.MainApplication

object ImageUtils {

	val widthScreen: Int
		get() = MainApplication.instance.displayMetrics.widthPixels

	val heightScreen: Int
		get() = MainApplication.instance.displayMetrics.heightPixels

	fun calculateImageSize(options: BitmapFactory.Options, reqWidth: Int = widthScreen, reqHeight: Int = heightScreen): Int {
		// Raw height and width of image
		val (height: Int, width: Int) = options.run { outHeight to outWidth }
		var inSampleSize = 1

		if (height > reqHeight || width > reqWidth) {
			val halfHeight: Int = height / 2
			val halfWidth: Int = width / 2

			// Calculate the largest inSampleSize value that is a power of 2 and keeps both
			// height and width larger than the requested height and width.
			while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
				inSampleSize *= 2
			}
		}
		return inSampleSize
	}

	fun decodeBitmapFromResource(uri: Uri, reqWidth: Int = widthScreen, reqHeight: Int = heightScreen): Bitmap? {
		val option = BitmapFactory.Options().apply {
			inJustDecodeBounds = true
		}
		if (option.outWidth == -1 || option.outHeight == -1) return null
		return MainApplication.instance.contentResolver.openInputStream(uri)?.use {
			option.inSampleSize = calculateImageSize(option, reqWidth, reqHeight)
			option.inJustDecodeBounds = false
			val srcBitMap = BitmapFactory.decodeStream(it, null, option)
			return srcBitMap!!
		}
	}
}
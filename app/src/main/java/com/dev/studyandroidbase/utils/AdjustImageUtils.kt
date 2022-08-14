package com.dev.studyandroidbase.utils

import android.graphics.*

object AdjustImageUtils {
	private const val DEFAULT_VALUE = 0f
	private const val ONE_VALUE = 1f
	const val BRIGHTNESS_DEFAULT = 100f
	const val CONTRAST_DEFAULT = 10f
	const val SATURATION_DEFAULT = 0.1f
	
	
	fun changeBitmapImageView(
		src: Bitmap,
		brightness: Float,
		contrast: Float,
		saturation: Float
	): Bitmap {
		// Create matrix 4x5
		val colorMatrix = ColorMatrix(
			floatArrayOf(
				contrast, saturation, DEFAULT_VALUE, DEFAULT_VALUE, brightness,
				DEFAULT_VALUE, contrast, DEFAULT_VALUE, DEFAULT_VALUE, brightness,
				DEFAULT_VALUE, DEFAULT_VALUE, contrast, DEFAULT_VALUE, brightness,
				DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE, ONE_VALUE, DEFAULT_VALUE
			)
		)
		
		val bitmap = Bitmap.createBitmap(src.width, src.height, src.config)
		val canvas = Canvas(bitmap)
		val paint = Paint(Paint.ANTI_ALIAS_FLAG)
		paint.colorFilter = ColorMatrixColorFilter(colorMatrix)
		canvas.drawBitmap(src, DEFAULT_VALUE, DEFAULT_VALUE, paint)
		return bitmap
	}
}
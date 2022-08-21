package com.dev.studyandroidbase.utils

import android.graphics.*
import android.widget.ImageView

object AdjustImageUtils {
	const val DEFAULT_VALUE = 0f
	const val ONE_VALUE = 1f
	const val BRIGHTNESS_DEFAULT = 100f
	const val CONTRAST_DEFAULT = 10f
	const val SATURATION_DEFAULT = 0.1f
	
	var originalMatrix = floatArrayOf(
		ONE_VALUE, DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE,
		ONE_VALUE, ONE_VALUE, DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE,
		ONE_VALUE, DEFAULT_VALUE, ONE_VALUE, DEFAULT_VALUE, DEFAULT_VALUE,
		ONE_VALUE, DEFAULT_VALUE, DEFAULT_VALUE, ONE_VALUE, DEFAULT_VALUE
	)

	fun adjustBrightnessAndContrast(
		src: Bitmap,
		brightness: Float,
		contrast: Float,
	): Bitmap {
		// Create matrix 4x5
		val colorMatrix = ColorMatrix(
			floatArrayOf(
				contrast, DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE, brightness,
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
	
	fun adjustBrightness(value: Float): ColorMatrix {
		val brightnessMatrix = floatArrayOf(
			ONE_VALUE, 0f, 0f, 0f, value,
			0f, ONE_VALUE, 0f, 0f, value,
			0f, 0f, ONE_VALUE, 0f, value,
			0f, 0f, 0f, 1f, 0f
		)
		return ColorMatrix(brightnessMatrix)
	}
	
	fun adjustContrast(value: Float): ColorMatrix {
		val valueContrast = value / 20
		val contrastMatrix = floatArrayOf(
			valueContrast, 0f, 0f, 0f, 0f,
			0f, valueContrast, 0f, 0f, 0f,
			0f, 0f, valueContrast, 0f, 0f,
			0f, 0f, 0f, 1f, 0f
		)
		return ColorMatrix(contrastMatrix)
	}

	fun originMatrix(): ColorMatrix {
		return ColorMatrix(
			floatArrayOf(
				ONE_VALUE, DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE,
				ONE_VALUE, ONE_VALUE, DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE,
				ONE_VALUE, DEFAULT_VALUE, ONE_VALUE, DEFAULT_VALUE, DEFAULT_VALUE,
				ONE_VALUE, DEFAULT_VALUE, DEFAULT_VALUE, ONE_VALUE, DEFAULT_VALUE
			)
		)
	}

	fun drawBitmap(colorMatrix: ColorMatrix, src: Bitmap): Bitmap {
		val copyBitmap = Bitmap.createScaledBitmap(src, src.width, src.height, true).copy(Bitmap.Config.ARGB_8888, true)
		Paint().apply {
			colorFilter = ColorMatrixColorFilter(colorMatrix)
			Canvas(copyBitmap).drawBitmap(src, 0f, 0f, this)
		}
		return copyBitmap
	}
}
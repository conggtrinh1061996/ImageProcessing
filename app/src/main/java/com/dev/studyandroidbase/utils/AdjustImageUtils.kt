package com.dev.studyandroidbase.utils

import android.graphics.*
import android.widget.ImageView

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

	fun adjustOrigin(value: Float, position: Int): ColorMatrix {
		val brightnessValue = value
		val contrastValue = 0.2f * value
		//
		val originColorMatrix = ColorMatrix()
		if (value != 0f) {
			when (position) {
				0 -> {
					originColorMatrix.postConcat(adjustBrightness(brightnessValue))
				}
				1 -> {
					originColorMatrix.postConcat(adjustContrast(contrastValue))
				}
			}
		}
		return ColorMatrix(originColorMatrix)
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

	fun drawBitmap(view: ImageView, colorMatrix: ColorMatrix) {
		val paint = Paint()
		paint.colorFilter = ColorMatrixColorFilter(colorMatrix)
		val copyBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.RGB_565)
		val canvas = Canvas(copyBitmap)
		canvas.drawBitmap(copyBitmap, 0f, 0f, paint)
		view.setImageBitmap(copyBitmap)
	}
	
}
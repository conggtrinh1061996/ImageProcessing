package com.dev.studyandroidbase.utils

import android.graphics.*
import com.dev.studyandroidbase.R
import com.dev.studyandroidbase.utils.Constants.FilterType.GRAY
import kotlin.math.max
import kotlin.math.min

object FilterUtils {
	
	fun sharpenBitmap(src: Bitmap): Bitmap {
		if (!src.isMutable) {
			return src
		}
		val laplacian = intArrayOf(-1, -1, -1, -1, 9, -1, 1, -1 ,-1)
		val width = src.width
		val height = src.height
		var pixR = 0
		var pixG = 0
		var pixB = 0
		var pixColor = 0
		var newR = 0
		var newG = 0
		var newB = 0
		var idx = 0
		val alpha = 0.3f
		val pixels = intArrayOf(width * height)
		
		src.getPixels(pixels, 0, width, 0, 0, width, height)
		for (i in 0 until (height - 1)) {
			for (k in 0 until (width - 1)) {
				idx = 0
				for (m in -1..1) {
					for (n in -1..1) {
						pixColor = pixels[(i + n) * width + k + m]
						pixR = Color.red(pixColor)
						pixG = Color.green(pixColor)
						pixB = Color.blue(pixColor)
						newR += ((pixR * laplacian[idx]) * alpha).toInt()
						newG += ((pixG * laplacian[idx]) * alpha).toInt()
						newB += ((pixB * laplacian[idx]) * alpha).toInt()
						++idx
					}
				}
				newR = min(255, max(0, newR))
				newG = min(255, max(0, newG))
				newB = min(255, max(0, newB))
				pixels[i * width + k] = Color.argb(255, newR, newG, newB)
				newR = 0
				newG = 0
				newB = 0
			}
		}
		src.setPixels(pixels, 0, width, 0, 0, width, height)
		return src
	}
	
	private fun grayImage(bitmap: Bitmap): Bitmap {
		val width = bitmap.width
		val height = bitmap.height
		val grayBitmap = Bitmap.createBitmap(width, height, bitmap.config)
		
		val canvas = Canvas(grayBitmap)
		canvas.drawBitmap(bitmap, Matrix(), Paint(Paint.ANTI_ALIAS_FLAG))
		for (i in 0 until width) {
			for (j in 0 until height) {
				val color: Int = bitmap.getPixel(i, j)
				val r = Color.red(color)
				val g = Color.green(color)
				val b = Color.blue(color)
				val a = Color.alpha(color)
				val value = (r * 0.3 + g * 0.59 + b * 0.11).toInt() //
				val newColor = Color.argb(a, value, value, value)
				grayBitmap.setPixel(i, j, newColor)
			}
		}
		return grayBitmap
	}
	
	private fun grayScale(): ColorMatrix {
		val grayMatrix = floatArrayOf(
			0.33f, 0.33f, 0.33f, 0f, 0f,
			0.33f, 0.33f, 0.33f, 0f, 0f,
			0.33f, 0.33f, 0.33f, 0f, 0f,
			0f, 0f, 0f, 1f, 0f
		)
		return ColorMatrix(grayMatrix)
	}
	
	fun filterType(bitmap: Bitmap, type: Int): Bitmap {
		when (type) {
			GRAY -> return grayImage(bitmap)
			else -> return grayImage(bitmap)
		}
		
	}
	
	fun filterImageType(type: Int): ColorMatrix {
		when (type) {
			GRAY -> return grayScale()
			else -> return grayScale()
		}
	}
}
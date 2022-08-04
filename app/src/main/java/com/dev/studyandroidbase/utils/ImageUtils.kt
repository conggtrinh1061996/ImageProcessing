package com.dev.studyandroidbase.utils

import android.graphics.Bitmap
import android.graphics.Bitmap.Config
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import com.dev.studyandroidbase.MainApplication
import java.io.*
import kotlin.math.max
import kotlin.math.min

object ImageUtils {

	val cacheDir by lazy { MainApplication.instance.cacheDir }

	private val appCacheDir by lazy {
		val file = File(cacheDir, "wonder_photo_cache_dir")
		if (!file.exists()) {
			file.mkdir()
		}
		file
	}

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

	fun saveBitmapToFile(bitmap: Bitmap, file: File): Boolean {
		val byteArrayOutStream = ByteArrayOutputStream()
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutStream)
		val byteArray = byteArrayOutStream.toByteArray()
		val fileOutputStream = FileOutputStream(file)
		return try {
			fileOutputStream.write(byteArray)
			true
		} catch (e: FileNotFoundException) {
			e.printStackTrace()
			false
		} catch (e2: IOException) {
			e2.printStackTrace()
			false
		} finally {
			fileOutputStream.flush()
			fileOutputStream.close()
		}
	}

	fun createTempFile(fileName: String): File {
		if (!appCacheDir.exists()) appCacheDir.mkdir()
		return File(appCacheDir, fileName)
	}
	
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
	
	fun getBitmapSharpened(src: Bitmap, recycleSrc: Boolean): Bitmap {
		val sharpened = sharpenBitmap(src.copy(Config.RGB_565, true))
		if (recycleSrc && sharpened != src) {
			src.recycle()
		}
		return sharpened
	}
}
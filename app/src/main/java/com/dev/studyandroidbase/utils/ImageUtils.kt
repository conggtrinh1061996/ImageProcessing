package com.dev.studyandroidbase.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.dev.studyandroidbase.MainApplication
import java.io.*

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
}
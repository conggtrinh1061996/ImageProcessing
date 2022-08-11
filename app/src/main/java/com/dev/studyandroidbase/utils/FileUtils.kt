package com.dev.studyandroidbase.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.graphics.BitmapCompat
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.util.*

object FileUtils {
	
	fun androidLargerThanQ(): Boolean {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
	}
	
	// first way
	fun saveImageToStorage(context: Context, name: String, bitmap: Bitmap?): Boolean {
		val imageCollection =
			if (androidLargerThanQ()) {
				MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
			} else MediaStore.Images.Media.EXTERNAL_CONTENT_URI
		
		val contentValues = ContentValues().apply {
			put(MediaStore.Images.Media.DISPLAY_NAME, "$name.jpg")
			put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
			if (bitmap != null) {
				put(MediaStore.Images.Media.WIDTH, bitmap.width)
				put(MediaStore.Images.Media.HEIGHT, bitmap.height)
			}
		}
		
		return try {
			context.contentResolver.insert(imageCollection, contentValues)?.also {
				
				context.contentResolver.openOutputStream(it).use { outputStream ->
					if (bitmap != null) {
						if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 95, outputStream)) {
							AppLogger.d("File Image Saved")
						}
					}
				}
			}
			true
		} catch (e: IOException) {
			return false
		}
	}
	
	// second way
	fun storeBitmapToExternalStorage(imageView: View, context: Context) {
		val fileName: String
		val bitmap = getBimapFromView(imageView)
		
		ByteArrayOutputStream().apply {
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, this)
			fileName = UUID.nameUUIDFromBytes(this.toByteArray()).toString().replace("-", "")
		}
		val imageFile =  File("${context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)}/ChatOut/$fileName.jpg/")
		
		if (!imageFile.exists()) {
			val contentResolver = ContentValues().apply {
				put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis())
				put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
				put(MediaStore.Images.Media.DATA, imageFile.absolutePath)
			}
			
			context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentResolver).apply {
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, context.contentResolver.openOutputStream(this!!))
			}
			Toast.makeText(context, "Image saved", Toast.LENGTH_SHORT).show()
		} else {
			Toast.makeText(context, "Image already saved", Toast.LENGTH_SHORT).show()
		}
	}
	
	private fun getBimapFromView(view: View): Bitmap {
		return Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888).apply {
			Canvas(this).apply {
				view.draw(this)
			}
		}
	}
}
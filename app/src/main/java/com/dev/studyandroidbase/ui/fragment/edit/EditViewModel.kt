package com.dev.studyandroidbase.ui.fragment.edit

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.viewModelScope
import com.dev.studyandroidbase.base.BaseViewModel
import com.dev.studyandroidbase.utils.AdjustImageUtils
import com.dev.studyandroidbase.utils.AdjustImageUtils.BRIGHTNESS_DEFAULT
import com.dev.studyandroidbase.utils.AdjustImageUtils.CONTRAST_DEFAULT
import com.dev.studyandroidbase.utils.AdjustImageUtils.SATURATION_DEFAULT
import com.dev.studyandroidbase.utils.FileUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class EditViewModel: BaseViewModel<EditNavigator>() {
	
	private fun adjustBrightness(view: ImageView, value: Float): Bitmap {
		return AdjustImageUtils.changeBitmapImageView(
			FileUtils.getBimapFromView(view),
			value,
			CONTRAST_DEFAULT,
			SATURATION_DEFAULT
		)
	}
	
	private fun adjustContrast(view: ImageView, value: Float): Bitmap {
		return AdjustImageUtils.changeBitmapImageView(
			FileUtils.getBimapFromView(view),
			BRIGHTNESS_DEFAULT,
			value,
			SATURATION_DEFAULT
		)
	}
	
	private fun adjustSaturation(view: ImageView, value: Float): Bitmap {
		return AdjustImageUtils.changeBitmapImageView(
			FileUtils.getBimapFromView(view),
			BRIGHTNESS_DEFAULT,
			CONTRAST_DEFAULT,
			value
		)
	}
	
	fun changeImageEffect(view: ImageView, position: Int, value: Float) {
		viewModelScope.launch {
			val deferred = async(Dispatchers.IO) {
				when (position) {
					0 -> {
						adjustBrightness(view, value)
					}
					
					1 -> {
						adjustContrast(view, value)
					}
					
					2 -> {
						adjustSaturation(view, value)
					}
					else -> {
						adjustBrightness(view, value)
					}
				}
			}
			val newBitmap = deferred.await()
			view.setImageBitmap(newBitmap)
		}
	}

}
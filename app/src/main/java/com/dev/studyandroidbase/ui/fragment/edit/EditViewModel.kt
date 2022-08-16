package com.dev.studyandroidbase.ui.fragment.edit

import android.graphics.Bitmap
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.View
import android.widget.ImageView
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
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
	
	/*private fun adjustBrightness(view: ImageView, value: Float): Bitmap {
		return AdjustImageUtils.changeBitmapImageView(
			FileUtils.getBimapFromView(view),
			value,
			CONTRAST_DEFAULT,
			SATURATION_DEFAULT
		)
	}*/
	
	fun changeImageEffect(position: Int, value: Float): ColorMatrix {
		return when (position) {
			0 -> AdjustImageUtils.adjustBrightness(value)
			1 -> AdjustImageUtils.adjustContrast(value)
			else -> AdjustImageUtils.adjustBrightness(value)
		}
	}
	
	fun getImageChanged(view: ImageView, value: Float) {
		viewModelScope.launch {
			val imageDeferred = viewModelScope.async(Dispatchers.IO) {
			
			}
			imageDeferred.await()
		}
	}

}
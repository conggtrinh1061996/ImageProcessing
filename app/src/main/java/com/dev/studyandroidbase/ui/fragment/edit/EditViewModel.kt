package com.dev.studyandroidbase.ui.fragment.edit

import android.graphics.*
import android.widget.ImageView
import androidx.lifecycle.viewModelScope
import com.dev.studyandroidbase.base.BaseViewModel
import com.dev.studyandroidbase.utils.AdjustImageUtils
import com.dev.studyandroidbase.utils.AdjustImageUtils.DEFAULT_VALUE
import com.dev.studyandroidbase.utils.AdjustImageUtils.ONE_VALUE
import com.dev.studyandroidbase.utils.AdjustImageUtils.adjustBrightnessAndContrast
import com.dev.studyandroidbase.utils.Constants.AdjustConstant.BRIGHTNESS
import com.dev.studyandroidbase.utils.Constants.AdjustConstant.CONTRAST
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class EditViewModel: BaseViewModel<EditNavigator>() {
	
	var originMatrix = floatArrayOf(
		ONE_VALUE, DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE,
		DEFAULT_VALUE, ONE_VALUE, DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE,
		DEFAULT_VALUE, DEFAULT_VALUE, ONE_VALUE, DEFAULT_VALUE, DEFAULT_VALUE,
		DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE, ONE_VALUE, DEFAULT_VALUE
	)
	var colorMatrixes: ColorMatrix? = null
	
	init {
		colorMatrixes = ColorMatrix()
	}
	
	/*private fun adjustBrightness(view: ImageView, value: Float): Bitmap {
		return AdjustImageUtils.changeBitmapImageView(
			FileUtils.getBimapFromView(view),
			value,
			CONTRAST_DEFAULT,
			SATURATION_DEFAULT
		)
	}*/
	
	fun changeImageEffect(position: Int, value: Float): ColorMatrix {
		val colorMatrix = ColorMatrix(originMatrix)
		when (position) {
			BRIGHTNESS -> {
				for (index in 4..originMatrix.size step 5) {
					if (index != originMatrix.size - 1) {
						originMatrix[index] = value
					}
				}
			}
			CONTRAST -> {
				repeat(originMatrix.count()) { index ->
					if (index % 6 == 0 && index != originMatrix.size - 2) {
						originMatrix[index] = value / 20
					}
				}
			}
		}
		colorMatrix.set(originMatrix)
		return colorMatrix
	}
	
	fun getImageChanged(view: ImageView, value: Float) {
		viewModelScope.launch {
			val imageDeferred = viewModelScope.async(Dispatchers.IO) {
			
			}
			imageDeferred.await()
		}
	}
	
	fun setImageEffect(position: Int, value: Float, src: Bitmap): Bitmap {
		when (position) {
			0 -> colorMatrixes!!.postConcat(AdjustImageUtils.adjustBrightness(value))
			1 -> colorMatrixes!!.postConcat(AdjustImageUtils.adjustContrast(value * 0.2f))
			else -> AdjustImageUtils.adjustBrightness(value)
		}
		return AdjustImageUtils.drawBitmap(colorMatrixes!!, src)
	}

}
package com.dev.studyandroidbase.ui.fragment.home

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.Config.RGB_565
import android.graphics.ColorMatrixColorFilter
import android.view.View
import android.widget.ImageView
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dev.studyandroidbase.base.BaseViewModel
import com.dev.studyandroidbase.base.UseCase.None
import com.dev.studyandroidbase.data.model.Mars
import com.dev.studyandroidbase.domain.usecase.FetchMarsUseCase
import com.dev.studyandroidbase.utils.AppLogger
import com.dev.studyandroidbase.utils.FileUtils
import com.dev.studyandroidbase.utils.FilterUtils
import com.dev.studyandroidbase.utils.ImageUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
	private val fetchMarsUseCase: FetchMarsUseCase
): BaseViewModel<HomeNavigator>() {
	
	private var _listMars: MutableLiveData<List<Mars>> = MutableLiveData()
	val listMars: LiveData<List<Mars>> get() = _listMars
	val isLoading = ObservableBoolean(false)
	
	init {
		fetchMars()
	}
	
	fun fetchMars(
	) {
		fetchMarsUseCase(None()) {
			it.fold({
				AppLogger.d("Failed get data")
			},
			{ list ->
				_listMars.postValue(list)
			})
			
		}
	}
	
	fun progressGrayImage(view: ImageView) {
		viewModelScope.launch {
			isLoading.set(true)
			val originDeferred = viewModelScope.async(Dispatchers.IO) { ImageUtils.getOriginBitmap() }
			val originBitmap = originDeferred.await()
			//
			val filteredDeferred = viewModelScope.async(Dispatchers.IO) { FilterUtils.filterType(originBitmap) }
			val filteredBitmap = filteredDeferred.await()
			//
			isLoading.set(false)
			view.setImageBitmap(filteredBitmap)
		}
	}
	
	fun progressImageFilter(view: ImageView, position: Int) {
		viewModelScope.launch {
			isLoading.set(true)
			val filterDeferred = viewModelScope.async(Dispatchers.IO) { FilterUtils.filterImageType(position) }
			val colorMatrix = filterDeferred.await()
			isLoading.set(false)
			view.colorFilter = ColorMatrixColorFilter(colorMatrix)
		}
	}
	
	fun saveImage(view: View, context: Context) {
		viewModelScope.launch {
			isLoading.set(true)
			val deferred = async { FileUtils.storeBitmapToExternalStorage(view, context) }
			
			deferred.await()
			isLoading.set(false)
		}
	}
}
package com.dev.studyandroidbase.ui.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dev.studyandroidbase.base.BaseViewModel
import com.dev.studyandroidbase.base.UseCase.None
import com.dev.studyandroidbase.data.model.Mars
import com.dev.studyandroidbase.domain.usecase.FetchMarsUseCase
import com.dev.studyandroidbase.utils.AppLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
	private val fetchMarsUseCase: FetchMarsUseCase
): BaseViewModel<HomeNavigator>() {
	
	private var _listMars: MutableLiveData<List<Mars>> = MutableLiveData()
	val listMars: LiveData<List<Mars>> get() = _listMars
	
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
	
}
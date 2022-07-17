package com.dev.studyandroidbase.ui.fragment

import com.dev.studyandroidbase.base.BaseViewModel
import com.dev.studyandroidbase.domain.usecase.FetchMarsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
	private val fetchMarsUseCase: FetchMarsUseCase
): BaseViewModel<HomeNavigator>() {
	
	fun fetchMars() {
	
	}
	
}
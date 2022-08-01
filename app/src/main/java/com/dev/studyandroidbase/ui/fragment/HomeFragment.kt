package com.dev.studyandroidbase.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.dev.studyandroidbase.R
import com.dev.studyandroidbase.base.BaseFragment
import com.dev.studyandroidbase.databinding.FragmentHomeBinding
import com.dev.studyandroidbase.extension.lifecycle.ResultLifeCycleObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding, HomeViewModel>(), HomeNavigator {
	
	lateinit var observer: ResultLifeCycleObserver
	
	override fun layoutId(): Int = R.layout.fragment_home
	
	override val viewModel: HomeViewModel by viewModels()
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		observer = ResultLifeCycleObserver(requireActivity().activityResultRegistry)
		lifecycle.addObserver(observer)
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		// set click mainImage
		binding.mainImage.setOnClickListener {
			observer.selectImage()
		}
	}
	
}
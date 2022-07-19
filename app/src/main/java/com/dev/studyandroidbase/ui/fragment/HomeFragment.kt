package com.dev.studyandroidbase.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.dev.studyandroidbase.R
import com.dev.studyandroidbase.base.BaseFragment
import com.dev.studyandroidbase.databinding.FragmentHomeBinding
import com.dev.studyandroidbase.utils.AppLogger
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding, HomeViewModel>(), HomeNavigator {
	
	override fun layoutId(): Int = R.layout.fragment_home
	
	override val viewModel: HomeViewModel by viewModels()
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.apply {
			viewModel.listMars.observe(viewLifecycleOwner) {
				AppLogger.d("ListMars: $it")
			}
		}
	}
	
}
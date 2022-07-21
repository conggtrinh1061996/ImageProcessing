package com.dev.studyandroidbase.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.dev.studyandroidbase.R
import com.dev.studyandroidbase.base.BaseFragment
import com.dev.studyandroidbase.databinding.FragmentHomeBinding
import com.dev.studyandroidbase.ui.adapter.ItemPhotoAdapter
import com.dev.studyandroidbase.utils.AppLogger
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding, HomeViewModel>(), HomeNavigator {
	
	override fun layoutId(): Int = R.layout.fragment_home
	
	override val viewModel: HomeViewModel by viewModels()
	
	val adaper = ItemPhotoAdapter()
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.apply {
			viewModel.listMars.observe(viewLifecycleOwner) {
				adaper.setData(it)
			}
		}
		setUpRecyclerView()
	}
	
	private fun setUpRecyclerView() {
		binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
		binding.recyclerView.adapter = adaper
	}
	
}
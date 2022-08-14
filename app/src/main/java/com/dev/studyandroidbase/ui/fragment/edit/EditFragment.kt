package com.dev.studyandroidbase.ui.fragment.edit

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dev.studyandroidbase.R
import com.dev.studyandroidbase.base.BaseFragment
import com.dev.studyandroidbase.data.local.prefs.PreferenceHelper
import com.dev.studyandroidbase.databinding.FragmentEditBinding
import com.dev.studyandroidbase.ui.adapter.AdjustImageAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditFragment: BaseFragment<FragmentEditBinding, EditViewModel>() {
	
	override val viewModel: EditViewModel by viewModels()
	
	override fun layoutId(): Int = R.layout.fragment_edit
	
	lateinit var adapter: AdjustImageAdapter
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setUpAdapter()
		// get image to edit
		getImageEdit()
		// click btn cancel
		binding.btnCancel.setOnClickListener {
			findNavController().popBackStack()
		}
	}
	
	private fun getImageEdit() {
		val imageUri = PreferenceHelper.getInstance().uriMainImage
		if (imageUri.isEmpty()) {
			binding.imageEdit.setImageResource(R.drawable.background_main_image)
		} else {
			val bitmapImage = BitmapFactory.decodeFile(imageUri)
			binding.imageEdit.setImageBitmap(bitmapImage)
		}
	}
	
	private fun setUpAdapter() {
		adapter = AdjustImageAdapter(requireContext())
		binding.recyclerViewAjust.adapter = adapter
		adapter.onItemClick = { position ->
			binding.recyclerViewAjust.isVisible = true
			binding.slider.isVisible = true
			sliderChangeValue(position)
		}
	}
	
	private fun sliderChangeValue(position: Int) {
		binding.apply {
			slider.addOnChangeListener { _, value, _ ->
				viewModel.changeImageEffect(binding.imageEdit, position, value)
			}
		}
	}
}
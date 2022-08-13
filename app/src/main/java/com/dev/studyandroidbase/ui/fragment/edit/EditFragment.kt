package com.dev.studyandroidbase.ui.fragment.edit

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dev.studyandroidbase.R
import com.dev.studyandroidbase.base.BaseFragment
import com.dev.studyandroidbase.data.local.prefs.PreferenceHelper
import com.dev.studyandroidbase.databinding.FragmentEditBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditFragment: BaseFragment<FragmentEditBinding, EditViewModel>() {
	
	override val viewModel: EditViewModel by viewModels()
	
	override fun layoutId(): Int = R.layout.fragment_edit
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
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
}
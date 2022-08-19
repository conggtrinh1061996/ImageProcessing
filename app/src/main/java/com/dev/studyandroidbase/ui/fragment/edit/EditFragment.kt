package com.dev.studyandroidbase.ui.fragment.edit

import android.graphics.*
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.databinding.ObservableBoolean
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dev.studyandroidbase.R
import com.dev.studyandroidbase.base.BaseFragment
import com.dev.studyandroidbase.data.local.prefs.PreferenceHelper
import com.dev.studyandroidbase.databinding.FragmentEditBinding
import com.dev.studyandroidbase.ui.adapter.AdjustImageAdapter
import com.dev.studyandroidbase.utils.AdjustImageUtils
import com.dev.studyandroidbase.utils.AppLogger
import com.google.android.material.slider.Slider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditFragment: BaseFragment<FragmentEditBinding, EditViewModel>() {
	
	override val viewModel: EditViewModel by viewModels()
	
	override fun layoutId(): Int = R.layout.fragment_edit
	
	lateinit var adapter: AdjustImageAdapter
	private var isEdited = false

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setUpAdapter()
		// get image to edit
		getImageEdit()
		// click btn cancel
		binding.btnCancel.setOnClickListener {
			findNavController().popBackStack()
			resetScreen()
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
			binding.apply {
				slider.isVisible = true
				btnSaveAll.isVisible = true
				btnDone.apply {
					isVisible = true
					setOnClickListener {
						resetScreen()
						/*val bitmapEdited = viewModel.setImageEffect(position, binding.slider.value, bitmap!!)
						binding.imageEdit.setImageBitmap(bitmapEdited)*/
					}
				}
			}
			//
			sliderChangeValue(position)
		}
	}
	
	private fun sliderChangeValue(position: Int) {
		binding.apply {
			slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
				override fun onStartTrackingTouch(slider: Slider) {
				}
				
				override fun onStopTrackingTouch(slider: Slider) {
					binding.imageEdit.apply {
					}
				}
			})
			//
			slider.addOnChangeListener { _, value, _ ->
				isEdited = value != 0f
				val colorMatrixFilter = viewModel.changeImageEffect(position, value * 1.1f)
				binding.imageEdit.colorFilter = ColorMatrixColorFilter(colorMatrixFilter)
			}
		}
	}
	
	private fun resetScreen() {
		binding.apply {
			slider.isVisible = false
			btnSaveAll.isVisible = false
			btnDone.isVisible = false
		}
	}

}
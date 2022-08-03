package com.dev.studyandroidbase.ui.fragment

import android.Manifest
import android.graphics.*
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.fragment.app.viewModels
import com.dev.studyandroidbase.R
import com.dev.studyandroidbase.base.BaseFragment
import com.dev.studyandroidbase.data.local.prefs.PreferenceHelper
import com.dev.studyandroidbase.databinding.FragmentHomeBinding
import com.dev.studyandroidbase.utils.ImageUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding, HomeViewModel>(), HomeNavigator {
	
	
	override fun layoutId(): Int = R.layout.fragment_home
	
	override val viewModel: HomeViewModel by viewModels()
	var bitmap: Bitmap? = null
	
	private val getContent = registerForActivityResult(GetContent()) { uri ->
		bitmap = ImageUtils.decodeBitmapFromResource(uri!!)
		binding.mainImage.setImageBitmap(bitmap)
		if (saveDataImage(uri)) {
			PreferenceHelper.getInstance().uriMainImage = uriPath
		}
	}

	private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
		if (isGranted) {
			getContent.launch("image/*")
		}
	}
	private var uriPath = ""
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		getImageSaved()
		// set click mainImage
		binding.mainImage.setOnClickListener {
			requestPermissionLauncher.launch(
				Manifest.permission.WRITE_EXTERNAL_STORAGE
			)
		}
	}

	private fun getImageSaved() {
		val uriImage = PreferenceHelper.getInstance().uriMainImage
		if (uriImage.isEmpty()) {
			binding.mainImage.setImageResource(R.drawable.background_main_image)
		} else {
			val bitmapImage = BitmapFactory.decodeFile(uriImage)
			binding.mainImage.setImageBitmap(bitmapImage)
		}
	}

	private fun saveDataImage(uri: Uri): Boolean {
		val bitmap = ImageUtils.decodeBitmapFromResource(uri)
		val fileName = ImageUtils.createTempFile("image_saved")
		uriPath = fileName.path
		return ImageUtils.saveBitmapToFile(bitmap!!, fileName)
	}
}
package com.dev.studyandroidbase.ui.fragment

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.fragment.app.viewModels
import com.dev.studyandroidbase.R
import com.dev.studyandroidbase.base.BaseFragment
import com.dev.studyandroidbase.data.local.prefs.PreferenceHelper
import com.dev.studyandroidbase.databinding.FragmentHomeBinding
import com.dev.studyandroidbase.extension.lifecycle.ResultLifeCycleObserver
import com.dev.studyandroidbase.utils.AppLogger
import com.dev.studyandroidbase.utils.ImageUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding, HomeViewModel>(), HomeNavigator {
	
	
	override fun layoutId(): Int = R.layout.fragment_home
	
	override val viewModel: HomeViewModel by viewModels()
	
	private val getContent = registerForActivityResult(GetContent()) { uri ->
		PreferenceHelper.getInstance().uriMainImage = uri.toString()
		val bitmap = ImageUtils.decodeBitmapFromResource(uri!!)
		binding.mainImage.setImageBitmap(bitmap)
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		// set click mainImage
		binding.mainImage.setOnClickListener {
			getContent.launch("image/*")
		}
	}
	
}
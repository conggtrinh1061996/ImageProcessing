package com.dev.studyandroidbase.ui.fragment

import android.Manifest
import android.animation.*
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
import com.dev.studyandroidbase.ui.adapter.ItemFilterAdapter
import com.dev.studyandroidbase.utils.ImageUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding, HomeViewModel>(), HomeNavigator {
	
	
	override fun layoutId(): Int = R.layout.fragment_home
	
	override val viewModel: HomeViewModel by viewModels()
	var bitmap: Bitmap? = null
	
	private val coroutineScope = CoroutineScope(Dispatchers.Main)
	private var adapter: ItemFilterAdapter? = null
	
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
	private var job: Job? = null
	private var buttonAnimation: Animator? = null
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.apply {
			vm = viewModel
			adapter = ItemFilterAdapter(requireContext())
			recyclerFilter.adapter = adapter
		}
		getImageSaved()
		// set click mainImage
		binding.mainImage.setOnClickListener {
			requestPermissionLauncher.launch(
				Manifest.permission.WRITE_EXTERNAL_STORAGE
			)
		}
		//
		setUpClickTest()
		// progress image
		binding.btnFilter.setOnClickListener {
			viewModel.progressImageFilter(binding.mainImage, 0)
		}
		// listener click
		adapter!!.itemClick = { position ->
			viewModel.progressImage(binding.mainImage, position)
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

	private fun setUpClickTest() {
		job?.cancel()
		job = CoroutineScope(Dispatchers.IO).launch {
			withContext(Dispatchers.Main) {
				startAnimation()
			}
		}
	}

	private fun startAnimation() {
		buttonAnimation?.apply {
			if (!isRunning) {
				cancel()
				start()
			}
		}
	}

	/*private fun heartAnimate() {
		val heartAnimator = ObjectAnimator.ofPropertyValuesHolder(
			binding.iconHeart,
			PropertyValuesHolder.ofFloat("scaleX", 1f, 2f, 1f),
			PropertyValuesHolder.ofFloat("scaleY", 1f, 2f, 1f)
		).setDuration(800)
		val animator = AnimatorSet()
		animator.startDelay = 1000
		buttonAnimation = animator
		animator.play(heartAnimator)
		animator.addListener(object : AnimatorListenerAdapter() {
			private var mCancel = false
			override fun onAnimationStart(animation: Animator?) {
				mCancel = false
			}

			override fun onAnimationEnd(animation: Animator?) {
				if (!mCancel) startAnimation()
			}

			override fun onAnimationCancel(animation: Animator?) {
				mCancel = true
				binding.iconHeart.scaleX = 1f
				binding.iconHeart.scaleY = 1f
			}
		})
	}*/
	
	private fun cancelAnimation() {
		buttonAnimation?.cancel()
	}

	override fun onDestroyView() {
		super.onDestroyView()
		cancelAnimation()
	}

}
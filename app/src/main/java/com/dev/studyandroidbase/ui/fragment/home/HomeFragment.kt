package com.dev.studyandroidbase.ui.fragment.home

import android.animation.*
import android.content.pm.PackageManager
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dev.studyandroidbase.R
import com.dev.studyandroidbase.base.BaseFragment
import com.dev.studyandroidbase.data.local.prefs.PreferenceHelper
import com.dev.studyandroidbase.databinding.FragmentHomeBinding
import com.dev.studyandroidbase.ui.adapter.ItemFilterAdapter
import com.dev.studyandroidbase.utils.Constants.permissions.READ_EXTERNAL_STORAGE
import com.dev.studyandroidbase.utils.Constants.permissions.WRITE_EXTERNAL_STORAGE
import com.dev.studyandroidbase.utils.ImageUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding, HomeViewModel>(), HomeNavigator {
	
	
	override fun layoutId(): Int = R.layout.fragment_home
	
	override val viewModel: HomeViewModel by viewModels()
	var bitmap: Bitmap? = null
	
	private var adapter: ItemFilterAdapter? = null
	private var isReadPermisstionGranted = false
	private var isWritePermisstionGranted = false
	
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
	
	private var permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
		
		isReadPermisstionGranted = permissions[READ_EXTERNAL_STORAGE] ?: isReadPermisstionGranted
		isWritePermisstionGranted = permissions[WRITE_EXTERNAL_STORAGE] ?: isReadPermisstionGranted
		
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
				WRITE_EXTERNAL_STORAGE
			)
		}
		//
		setUpClickTest()
		// listener click
		adapter!!.itemClick = { position ->
			viewModel.progressImageFilter(binding.mainImage, position)
			binding.recyclerFilter.smoothScrollToPosition(position)
			binding.imgDone.apply {
				setBackgroundResource(
					if (position == 0) R.drawable.bg_unchecked_save else R.drawable.bg_checked_save
				)
				setOnClickListener {
					if (position != 0) {
						requestPermission()
						viewModel.saveImage(binding.mainImage, requireContext())
					}
				}
			}
		}
		// Click button edit image
		binding.btnEdit.setOnClickListener {
			findNavController().navigate(R.id.action_home_to_edit)
		}
		// Gray image effect
		grayImage()
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
	
	private fun requestPermission() {
		val isReadPermission = ContextCompat.checkSelfPermission(
			requireContext(), READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
		
		val isWritePermission = ContextCompat.checkSelfPermission(
			requireContext(), WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
		
		val minSdkLevel = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
		
		isReadPermisstionGranted = isReadPermission
		isWritePermisstionGranted = isWritePermission || minSdkLevel
		
		val permissionRequest = mutableListOf<String>()
		
		if (!isWritePermisstionGranted) {
			permissionRequest.add(WRITE_EXTERNAL_STORAGE)
		}
		if (!isReadPermisstionGranted) {
			permissionRequest.add(READ_EXTERNAL_STORAGE)
		}
		
		if (permissionRequest.isNotEmpty()) {
			permissionLauncher.launch(permissionRequest.toTypedArray())
		}
	}

	fun grayImage() {
		binding.btnGray.setOnClickListener {
			viewModel.progressGrayImage(binding.mainImage)
		}
	}

}
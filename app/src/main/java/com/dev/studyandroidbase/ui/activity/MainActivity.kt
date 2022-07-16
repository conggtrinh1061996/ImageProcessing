package com.dev.studyandroidbase.ui.activity

import android.os.Bundle
import com.dev.studyandroidbase.R
import com.dev.studyandroidbase.base.BaseActivity
import com.dev.studyandroidbase.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator {
	val TAG = "MainApp"
	
	override fun layoutId(): Int = R.layout.activity_main
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		viewModel.setNavigator(this)
	}
	
}
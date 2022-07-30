package com.dev.studyandroidbase.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import com.dev.studyandroidbase.R
import com.dev.studyandroidbase.base.BaseActivity
import com.dev.studyandroidbase.databinding.ActivityMainBinding
import com.dev.studyandroidbase.notification.NotificationSchedule
import com.dev.studyandroidbase.utils.AppLogger
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator {
	val TAG = "MainApp"
	
	override fun layoutId(): Int = R.layout.activity_main
	
	override val viewModel: MainViewModel by viewModels()
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		NotificationSchedule.scheduleFirstNotify(this)
	}
	
	
}
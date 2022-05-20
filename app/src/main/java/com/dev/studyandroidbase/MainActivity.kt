package com.dev.studyandroidbase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.dev.studyandroidbase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
	val TAG = "MainApp"
	private lateinit var binding: ActivityMainBinding
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
		//

	}


}
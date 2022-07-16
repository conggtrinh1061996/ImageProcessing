package com.dev.studyandroidbase.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<VB: ViewDataBinding, VM: BaseViewModel<*>>: AppCompatActivity() {
	
	protected lateinit var binding: VB
	protected lateinit var viewModel: VM
	
	abstract fun layoutId(): Int
	
	override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
		super.onCreate(savedInstanceState, persistentState)
		binding = DataBindingUtil.setContentView(this, layoutId())
		binding.apply {
			lifecycleOwner = this@BaseActivity
		}
	}
}
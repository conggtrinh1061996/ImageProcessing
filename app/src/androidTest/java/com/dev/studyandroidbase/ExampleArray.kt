package com.dev.studyandroidbase

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.util.*
import java.util.Collections.binarySearch

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleArray {
	val TAG = "DemoTestArray: "
	
	@Test
	fun useAppContext() {
		// Context of the app under test.
		val appContext = InstrumentationRegistry.getInstrumentation().targetContext
		assertEquals("com.dev.studyandroidbase", appContext.packageName)
	}
	
	private fun printLog(param: Any) {
		Log.d(TAG, "$param\n")
	}
	
	@Test
	fun sortedAndSearchArray() {
		val list = mutableListOf(1, 2, 3, 3, 10, 6, 7, 7)
		printLog(list)
		// sau khi sorted
		list.sort()
		printLog(list)
		// tim kiem nhi phan
		printLog(list.binarySearch(7))
		printLog(list.binarySearch(5))
		printLog(list.binarySearch(2, 6))
	}
}
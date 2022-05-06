package com.dev.studyandroidbase

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
	val TAG = "DemoTestString: "
	
	private fun printLog(param: Any) {
		Log.d(TAG, "$param\n")
	}
	
	@Test
	fun useAppContext() {
		// Context of the app under test.
		val appContext = InstrumentationRegistry.getInstrumentation().targetContext
		assertEquals("com.dev.studyandroidbase", appContext.packageName)
	}
	
	@Test
	fun lastIndexOfString() {
		val strOrigion = "Hello world, I love Android"
		val subStr1 = "Hello"
		val subStr2 = "world,"
		val subStr3 = "I love"
		val subStr4 = "Android"
		printLog(strOrigion.lastIndexOf(subStr1))
		printLog(strOrigion.lastIndexOf(subStr2))
		printLog(strOrigion.lastIndexOf(subStr3))
		printLog(strOrigion.lastIndexOf(subStr4))
	}
	
	@Test
	fun compare2String() {
		val str1 = "Hello world"
		val str2 = "hello World"
		printLog( str1.compareTo(str2))
		printLog(str1.compareTo(str2, true))
	}
	
	@Test
	fun replaceStringAt() {
		val str = "Hello world Android"
		printLog(str.replace('l', 'I'))
		printLog(str.replaceFirst("He", "T"))
		printLog(str.replaceAfterLast("Android", "IOS"))
		printLog(str.replaceAfterLast("And", "IOS"))
	}
	
	@Test
	fun reverseStrings() {
		val string = "Trinh"
		val mutableString = StringBuffer(string)
		printLog(mutableString.reverse())
	}
}
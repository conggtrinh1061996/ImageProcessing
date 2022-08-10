package com.dev.studyandroidbase.test

import android.annotation.SuppressLint
import com.dev.studyandroidbase.utils.AppLogger
import kotlinx.coroutines.*
import java.text.SimpleDateFormat


@DelicateCoroutinesApi
@SuppressLint("SimpleDateFormat")
fun main() = runBlocking {
	val time = System.currentTimeMillis()
	val timeStart = SimpleDateFormat("HH:mm:ss").format(time)
	println("Start.....${timeStart}")
	coroutineScope {
		val deferred = listOf(
			async { networkCall1() },
			async { networkCall2() }
		)
		deferred.awaitAll()
		println("Response 1: ${deferred[0].await()}")
		println("Response 2: ${deferred[1].await()}")

	}
	println("End.....: ${System.currentTimeMillis() - time}")

}

suspend fun networkCall1(): String {
	delay(2000L)
	return "Answer 1"
}

suspend fun networkCall2(): String {
	delay(3000L)
	return "Answer 2"
}

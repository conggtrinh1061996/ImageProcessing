package com.dev.studyandroidbase.notification

import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.dev.studyandroidbase.notification.worker.FirstNotificationWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

object NotificationSchedule {

	fun scheduleFirstNotify(context: Context) {
		CoroutineScope(Dispatchers.IO).launch {
			val constraints = Constraints.Builder()
				.setRequiredNetworkType(NetworkType.NOT_REQUIRED)
				.setRequiresBatteryNotLow(false)
				.setRequiresCharging(true)
				.build()

			val workManager = WorkManager.getInstance(context)

			val workRequest = PeriodicWorkRequest.Builder(
				FirstNotificationWorker::class.java, 15, TimeUnit.MINUTES).setConstraints(constraints).build()
			workManager.enqueue(workRequest)
		}
	}
}
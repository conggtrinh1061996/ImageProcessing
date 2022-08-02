package com.dev.studyandroidbase.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.dev.studyandroidbase.R
import com.dev.studyandroidbase.notification.worker.FirstNotificationWorker
import com.dev.studyandroidbase.notification.worker.FirstNotificationWorker.Companion.CHANNEL_ID
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
				FirstNotificationWorker::class.java, 1, TimeUnit.HOURS).setConstraints(constraints).build()
			workManager.enqueue(workRequest)
		}
	}
	
	fun createNotificationChannel(context: Context) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			val mChannel = NotificationChannel(
				CHANNEL_ID,
				context.getString(R.string.app_name),
				NotificationManager.IMPORTANCE_DEFAULT
			)
			mChannel.description = context.getString(R.string.app_name)
			val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
			notificationManager.createNotificationChannel(mChannel)
		}
	}
}
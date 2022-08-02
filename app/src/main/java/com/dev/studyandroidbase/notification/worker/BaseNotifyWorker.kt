package com.dev.studyandroidbase.notification.worker

import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.dev.studyandroidbase.R

abstract class BaseNotifyWorker(
	private var context: Context,
	parameters: WorkerParameters
): Worker(context, parameters) {

	override fun doWork(): Result {

		val notificationBuilder = createNotification() ?: return Result.failure()

		notificationBuilder.setSmallIcon(R.drawable.ic_favorite)
			.setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.music_for_you))
			.setStyle(NotificationCompat.BigTextStyle())
			.setAutoCancel(true)

		val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
		notificationManager.notify(
			context.packageName,
			NOTIFICATION_ID,
			notificationBuilder.build()
		)

		return Result.success()
	}

	abstract fun createNotification(): NotificationCompat.Builder?

	companion object {
		const val NOTIFICATION_ID = 1
		const val KEY_ACTIVITY_NAME = "activity_name"
	}
}
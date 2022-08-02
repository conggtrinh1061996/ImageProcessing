package com.dev.studyandroidbase.notification.worker

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.WorkerParameters
import com.dev.studyandroidbase.ui.activity.MainActivity

class FirstNotificationWorker(
	context: Context,
	workerParameters: WorkerParameters
): BaseNotifyWorker(context, workerParameters) {

	override fun createNotification(): NotificationCompat.Builder? {
		val intent = Intent(applicationContext, MainActivity::class.java).apply {
			flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
		}

		val pendingIntent = PendingIntent.getActivity(
			applicationContext,
			1,
			intent,
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) 0 else PendingIntent.FLAG_IMMUTABLE
		)

		return NotificationCompat.Builder(applicationContext, CHANNEL_ID)
			.setContentTitle("First Notification")
			.setContentText("This is my first Notification")
			.setStyle(NotificationCompat.BigTextStyle())
			.setPriority(NotificationCompat.PRIORITY_DEFAULT)
			.setAutoCancel(true)
			.setContentIntent(pendingIntent)
	}

	companion object {
		const val CHANNEL_ID = "channel_id"
	}

}
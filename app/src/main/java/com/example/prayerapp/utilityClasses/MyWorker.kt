package com.example.prayerapp.utilityClasses

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.prayerapp.R
import com.example.prayerapp.fragments.PrayersRecordFragment

class MyWorker(context: Context, workerParameters: WorkerParameters):
    Worker(context, workerParameters) {

    private lateinit var workManager: WorkManager

    override fun doWork(): Result {
        showNotification()
        //Implement firebase code here
        workManager = WorkManager()
        workManager.uploadDataToFirebase()

        return Result.success()
    }

    /*
    Showing notification for WorkManager
     */

    private fun showNotification() {
        val intent = Intent(
            applicationContext,
            PrayersRecordFragment::class.java
        ).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            applicationContext, 0, intent,0
        )

        val notification = NotificationCompat.Builder(applicationContext, "my_unique_id")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("App Prayer")
            .setContentText("Uploading Data To Cloud")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        /*
        Checking android version
         */
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val channelName = "Channel Name"
            val channelDescription = "Channel Description"
            val channelImportance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel("my_unique_id", channelName, channelImportance)
                .apply {
                    description = channelDescription
                }

            val notificationManager = applicationContext.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
        with(NotificationManagerCompat.from(applicationContext)){
            notify(1, notification.build())
        }
    }

}
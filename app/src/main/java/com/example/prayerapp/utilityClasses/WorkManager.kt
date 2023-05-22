package com.example.prayerapp.utilityClasses

import android.content.Context
import android.util.Log
import androidx.work.*
import androidx.work.WorkManager
import com.google.firebase.database.FirebaseDatabase
import java.util.concurrent.TimeUnit

class WorkManager {

    var database = FirebaseDatabase.getInstance()
    /*
     Implementing Work Manager
    */
    fun myWorkManager(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiresCharging(false)
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresBatteryNotLow(true)
            .build()

        val myRequest = PeriodicWorkRequest.Builder(
            MyWorker::class.java,
            15,
            TimeUnit.MINUTES
        ).setConstraints(constraints)
            .build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                "my_id",
                ExistingPeriodicWorkPolicy.KEEP,
                myRequest
            )
    }

    fun simpleWork(context: Context){

        /*
        Initializing Work Manager
         */
        val mRequest: WorkRequest = OneTimeWorkRequestBuilder<MyWorker>()
            .build()

        WorkManager.getInstance(context)
            .enqueue(mRequest)
    }

    fun uploadDataToFirebase() {
        Log.d("Upload", "uploadDataToFirebase: Uploading Data To Firebase")
        // Write a message to the database
        val myRef = database.getReference("message")

        myRef.setValue("Hello, World!")
    }
}
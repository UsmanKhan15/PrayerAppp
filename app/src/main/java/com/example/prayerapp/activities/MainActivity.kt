package com.example.prayerapp.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.prayerapp.databinding.ActivityMainBinding
import com.example.prayerapp.utilityClasses.WorkManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var workManager: WorkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        workManager = WorkManager()

        workManager.simpleWork(this)

        workManager.myWorkManager(this)
        /*
        If user is using application first time
        The application will show a slider
        We are using sharedPreferences for this purpose
         */
        sharedPreferences = getSharedPreferences("onBoardingScreen", MODE_PRIVATE)

        val isFirstTime: Boolean = sharedPreferences.getBoolean("firstTime", true)
        if (isFirstTime) {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putBoolean("firstTime", false)
            editor.apply()

            val intent = Intent(this@MainActivity, SplashScreen::class.java)
            startActivity(intent)
            finish()
        }
    }
}
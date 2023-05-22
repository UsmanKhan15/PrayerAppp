package com.example.prayerapp.Data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.prayerapp.Model.PrayerRecord

@Database(entities = [PrayerRecord::class], version = 1)
abstract class PrayerDatabase: RoomDatabase() {
    abstract fun prayerDao(): PrayerDAO
}
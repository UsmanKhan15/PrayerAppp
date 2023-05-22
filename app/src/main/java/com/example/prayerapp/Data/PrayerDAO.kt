package com.example.prayerapp.Data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.prayerapp.Model.PrayerRecord

@Dao
interface PrayerDAO {

    @Query("SELECT * FROM userName")
    fun getAll(): LiveData<List<PrayerRecord>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(prayerRecord: PrayerRecord)

    @Query("UPDATE userName SET fajr = :value WHERE date = :date")
    suspend fun updateFajr(value: Boolean, date: Int)

    @Query("UPDATE userName SET dhuhr = :value WHERE date = :date")
    suspend fun updateDhuhr(value: Boolean, date: Int)

    @Query("UPDATE userName SET asr = :value WHERE date = :date")
    suspend fun updateAsr(value: Boolean, date: Int)

    @Query("UPDATE userName SET maghrib = :value WHERE date = :date")
    suspend fun updateMaghrib(value: Boolean, date: Int)

    @Query("UPDATE userName SET isha = :value WHERE date = :date")
    suspend fun updateIsha(value: Boolean, date: Int)

    @Query("SELECT * FROM userName WHERE month = :month")
    fun getAllByMonth(month: Int): LiveData<List<PrayerRecord>>

    @Query("SELECT * FROM userName WHERE weekYear = :week")
    fun getAllByWeek(week: Int): LiveData<List<PrayerRecord>>

    @Query("SELECT * FROM userName WHERE year = :year")
    fun getAllByYear(year: Int): LiveData<List<PrayerRecord>>
}
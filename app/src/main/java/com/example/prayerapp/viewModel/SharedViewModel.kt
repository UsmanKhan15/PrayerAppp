package com.example.prayerapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.prayerapp.Model.PrayerRecord
import com.example.prayerapp.Data.PrayerDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    var date: Int = 0
    var month: Int = 0
    var year: Int = 0
    var weekDay: Int = 0
    var yearDay: Int = 0
    var weekOfYear: Int = 0
    var dayName: String
    var state: Int = 0
    private var percentage: Int = 0
    private var total: Int = 0
    var listWeek: ArrayList<PrayerRecord>
    var listMonth: ArrayList<PrayerRecord>
    var listYear: ArrayList<PrayerRecord>
    var listAll: ArrayList<PrayerRecord>

    init{
        val calendar: Calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH) + 1
        date = calendar.get(Calendar.DAY_OF_MONTH)
        weekDay = calendar.get(Calendar.DAY_OF_WEEK) - 1
        yearDay = calendar.get(Calendar.DAY_OF_YEAR)
        weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR)
        dayName = getDayName(weekDay)
        listWeek = ArrayList()
        listMonth = ArrayList()
        listYear = ArrayList()
        listAll = ArrayList()
    }

    private fun getDayName(day: Int): String {
        return when(day) {
            1 -> "Monday"
            2 -> "Tuesday"
            3 -> "Wednesday"
            4 -> "Thursday"
            5 -> "Friday"
            6 -> "Saturday"
            7 -> "Sunday"
            else -> {
                ""
            }
        }
    }

    private val db = Room.databaseBuilder(
        application,
        PrayerDatabase::class.java, "database-name"
    ).build()



    fun addPrayer(prayerRecord: PrayerRecord){
        viewModelScope.launch(Dispatchers.IO) {
            db.prayerDao().addUser(prayerRecord)
        }
    }

    fun readAll(): LiveData<List<PrayerRecord>> = db.prayerDao().getAll()

    fun readByMonth(month: Int): LiveData<List<PrayerRecord>> = db.prayerDao().getAllByMonth(month)

    fun readByWeek(week: Int): LiveData<List<PrayerRecord>> = db.prayerDao().getAllByWeek(week)

    fun readByYear(year: Int): LiveData<List<PrayerRecord>> = db.prayerDao().getAllByYear(year)

    fun updateFajr(value: Boolean, date: Int){
        viewModelScope.launch(Dispatchers.IO) {
            db.prayerDao().updateFajr(value, date)
        }
    }

    fun updateDhuhr(value: Boolean, date: Int){
        viewModelScope.launch(Dispatchers.IO) {
            db.prayerDao().updateDhuhr(value, date)
        }
    }

    fun updateAsr(value: Boolean, date: Int){
        viewModelScope.launch(Dispatchers.IO) {
            db.prayerDao().updateAsr(value, date)
        }
    }

    fun updateMaghrib(value: Boolean, date: Int){
        viewModelScope.launch(Dispatchers.IO) {
            db.prayerDao().updateMaghrib(value, date)
        }
    }

    fun updateIsha(value: Boolean, date: Int){
        viewModelScope.launch(Dispatchers.IO) {
            db.prayerDao().updateIsha(value, date)
        }
    }

    private fun dataPrayedPrayers(listPrayer: ArrayList<PrayerRecord>): Int{
        for(index in listPrayer){
            if(index.fajr)
            {
                total++
            }
            if(index.dhuhr)
            {
                total++
            }
            if(index.asr)
            {
                total++
            }
            if(index.maghrib)
            {
                total++
            }
            if(index.isha)
            {
                total++
            }
            else
            {
                total
            }
        }
        return total
    }

    fun totalPrayersPercentage(listPrayer: ArrayList<PrayerRecord>, value: Int): Int{
        val totalPrayers: Int
        val totalDays: Int
        when (value) {
            1 -> {
                total = 0
                listWeek = listPrayer

                totalDays = listWeek.size

                totalPrayers = totalDays * 5
                val prayedPrayer: Int = dataPrayedPrayers(listPrayer)
                percentage = Integer.parseInt((100 * prayedPrayer / totalPrayers).toString())
                return percentage
            }
            2 -> {
                total = 0
                listMonth = listPrayer

                //Implement Month
                totalDays = date + 1 - listPrayer[0].date
                totalPrayers = totalDays * 5
                val prayedPrayer: Int = dataPrayedPrayers(listPrayer)
                percentage = Integer.parseInt((100 * prayedPrayer / totalPrayers).toString())
                return percentage
            }
            3 -> {
                //Implement Year
                total = 0
                listYear = listPrayer

                totalDays = yearDay + 1 - listPrayer[0].yearDay
                totalPrayers = totalDays * 5
                val prayedPrayer: Int = dataPrayedPrayers(listPrayer)
                percentage = Integer.parseInt((100 * prayedPrayer/totalPrayers).toString())
                return percentage
            }
            else -> {
                //Implement All
                total = 0
                listAll = listPrayer

                totalDays = yearDay + 1 - listPrayer[0].yearDay
                totalPrayers = totalDays * 5
                val prayedPrayer: Int = dataPrayedPrayers(listPrayer)
                percentage = Integer.parseInt((100 * prayedPrayer/totalPrayers).toString())
                return percentage
            }
        }
    }
}

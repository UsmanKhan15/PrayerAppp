package com.example.prayerapp.Model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "userName")
data class PrayerRecord(
    @PrimaryKey
    val yearDay: Int,
    val date: Int,
    val month: Int,
    val year: Int,
    val weekDay: Int,
    val weekYear: Int,
    val fajr: Boolean = false,
    val dhuhr: Boolean = false,
    val asr: Boolean = false,
    val maghrib: Boolean = false,
    val isha: Boolean = false
)

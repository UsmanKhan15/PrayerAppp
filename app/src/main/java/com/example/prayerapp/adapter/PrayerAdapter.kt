package com.example.prayerapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.prayerapp.Model.PrayerRecord
import com.example.prayerapp.R

class PrayerAdapter(val listPrayer: ArrayList<PrayerRecord>):
    RecyclerView.Adapter<PrayerAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listPrayer[position]
        holder.date.text = data.date.toString()
        holder.fajr.text = data.fajr.toString()
        holder.dhuhur.text = data.dhuhr.toString()
        holder.asr.text = data.asr.toString()
        holder.maghrib.text = data.maghrib.toString()
        holder.isha.text = data.isha.toString()
    }

    override fun getItemCount(): Int {
        return listPrayer.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.edtDateDisplay)
        val fajr: TextView = itemView.findViewById(R.id.edtFajrDisplay)
        val dhuhur: TextView = itemView.findViewById(R.id.edtDhuhurDisplay)
        val asr: TextView = itemView.findViewById(R.id.edtAsrDisplay)
        val maghrib: TextView = itemView.findViewById(R.id.edtMaghribDisplay)
        val isha: TextView = itemView.findViewById(R.id.edtIshaDisplay)
    }
}
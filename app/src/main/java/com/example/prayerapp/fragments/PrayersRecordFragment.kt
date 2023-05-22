package com.example.prayerapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import androidx.work.*
import com.example.prayerapp.Model.PrayerRecord
import com.example.prayerapp.R
import com.example.prayerapp.databinding.FragmentPrayersRecordBinding
import com.example.prayerapp.utilityClasses.MyWorker
import com.example.prayerapp.viewModel.SharedViewModel
import com.google.firebase.database.FirebaseDatabase
import java.util.concurrent.TimeUnit


class PrayersRecordFragment : Fragment() {

    private lateinit var binding: FragmentPrayersRecordBinding
    private val model: SharedViewModel by activityViewModels()
    private lateinit var listPrayer: ArrayList<PrayerRecord>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Inflate the layout for this fragment
        binding = FragmentPrayersRecordBinding.inflate(layoutInflater)

        /*
        Navigating from Record fragment to detail record fragment
         */
        binding.weekCard.setOnClickListener{
            model.state = 1
            findNavController(binding.root).navigate(R.id.action_prayersRecordFragment_to_detailRecordFragment)
        }

        binding.monthCard.setOnClickListener{
            model.state = 2
            findNavController(binding.root).navigate(R.id.action_prayersRecordFragment_to_detailRecordFragment)
        }

        binding.yearCard.setOnClickListener{
            model.state = 3
            findNavController(binding.root).navigate(R.id.action_prayersRecordFragment_to_detailRecordFragment)
        }

        binding.allRecordCard.setOnClickListener{
            model.state = 4
            findNavController(binding.root).navigate(R.id.action_prayersRecordFragment_to_detailRecordFragment)
        }

        /*
        Setting Percentage to Text View
         */
        val thisWeek: Int = model.weekOfYear
        val thisMonth: Int = model.month
        val thisYear: Int = model.year
        listPrayer = ArrayList()

        model.readByWeek(thisWeek).observe(viewLifecycleOwner) { prayer ->
            listPrayer = prayer as ArrayList<PrayerRecord>
            Log.d("PrayerWeek", "onCreateView: Week" + listPrayer.size)
            if (listPrayer.isEmpty()) {
                Toast.makeText(requireContext(), "We have no data to show", Toast.LENGTH_SHORT)
                    .show()
            } else {
                binding.txtWeekRecord.text =
                    model.totalPrayersPercentage(listPrayer, 1).toString() + "%"
            }
        }

        model.readByMonth(thisMonth).observe(viewLifecycleOwner, Observer {prayer->
            listPrayer = prayer as ArrayList<PrayerRecord>
            if(listPrayer.isEmpty())
            {
            }
            else
                binding.txtMonth.text =
                    model.totalPrayersPercentage(listPrayer, 2).toString() + "%"
        })

        model.readByYear(thisYear).observe(viewLifecycleOwner){prayer ->
            listPrayer = prayer as ArrayList<PrayerRecord>
            if(listPrayer.isEmpty())
            {

            }
            else
            {
                binding.txtYearRecord.text =
                    model.totalPrayersPercentage(listPrayer, 3).toString() + "%"
            }
        }

        model.readAll().observe(viewLifecycleOwner){prayer ->
            listPrayer = prayer as ArrayList<PrayerRecord>
            if(listPrayer.isEmpty())
            {

            }
            else
            {
                binding.txtAllRecord.text =
                    model.totalPrayersPercentage(listPrayer, 4).toString() + "%"
            }
        }

        return binding.root
    }
}
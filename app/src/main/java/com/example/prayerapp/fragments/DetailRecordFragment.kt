package com.example.prayerapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prayerapp.Model.PrayerRecord
import com.example.prayerapp.viewModel.SharedViewModel
import com.example.prayerapp.adapter.PrayerAdapter
import com.example.prayerapp.databinding.FragmentDetailRecordBinding

class DetailRecordFragment : Fragment() {

    private lateinit var binding: FragmentDetailRecordBinding
    private val model: SharedViewModel by activityViewModels()
    lateinit var adapter: PrayerAdapter
    lateinit var recordList: ArrayList<PrayerRecord>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailRecordBinding.inflate(layoutInflater, container, false)
        // Recyclerview
        if(model.state == 1)
        {
            recordList = model.listWeek
        }
        else if(model.state == 2)
        {
            recordList = model.listMonth
        }
        else if(model.state == 3)
        {
            recordList = model.listYear
        }
        else
        {
            recordList = model.listAll
        }

        adapter = PrayerAdapter(recordList)
        binding.recordRecView.layoutManager = LinearLayoutManager(requireContext())
        binding.recordRecView.adapter = adapter

        return binding.root
    }
}

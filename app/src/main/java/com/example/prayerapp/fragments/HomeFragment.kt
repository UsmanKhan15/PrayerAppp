package com.example.prayerapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.prayerapp.R
import com.example.prayerapp.databinding.FragmentHomeBinding
import com.example.prayerapp.utilityClasses.CustomDialog

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private lateinit var customDialog: CustomDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        /*
        Navigating from Home Fragment to Prayers Fragment
        and check that user is first time on app
         */
        customDialog = CustomDialog()
        binding?.approvePrayersCard?.setOnClickListener{
            customDialog.dialog(requireContext())
            Navigation.findNavController(binding!!.root).navigate(R.id.action_homeFragment_to_prayersFragment)
        }

        /*
        Navigating from Home Fragment to Record Fragment
         */
        binding?.trackPrayersRecord?.setOnClickListener{
            Navigation.findNavController(binding!!.root).navigate(R.id.action_homeFragment_to_prayersRecordFragment)
        }

        return binding!!.root
    }
}
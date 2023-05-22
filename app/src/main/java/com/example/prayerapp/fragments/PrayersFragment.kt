package com.example.prayerapp.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.prayerapp.Model.PrayerRecord
import com.example.prayerapp.R
import com.example.prayerapp.viewModel.SharedViewModel
import com.example.prayerapp.databinding.CustomCodeDialogueBinding
import com.example.prayerapp.databinding.FragmentPrayersBinding
import com.example.prayerapp.utilityClasses.CustomDialog

class PrayersFragment : Fragment() {

    private val model: SharedViewModel by activityViewModels()
    private lateinit var binding: FragmentPrayersBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var dialogCode: Dialog
    private lateinit var bindingDialog: CustomCodeDialogueBinding
    private lateinit var customDialog: CustomDialog

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPrayersBinding.inflate(layoutInflater, container, false)
        sharedPreferences = requireContext().getSharedPreferences("checkDate", MODE_PRIVATE)

        layoutInitializer()
        setPrayerData()

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun layoutInitializer() {

        customDialog = CustomDialog()
        //Checking Switch Statement
        checkSwitchState()

        binding.switchFajr.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                codeDialog(1)
            }
        }

        binding.switchZohr.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked) {
                codeDialog(2)
            }
        }

        binding.switchAshr.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked) {
                codeDialog(3)
            }
        }

        binding.switchMagrb.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked) {
                codeDialog(4)
            }
        }

        binding.switchEsha.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked) {
                codeDialog(5)
            }
        }

        //Adding Record Button
        binding.btnAddRecord.setOnClickListener{
            customDialog.dialog(requireContext())
            Navigation.findNavController(binding.root).navigate(R.id.action_prayersFragment_to_homeFragment)
            FragmentTransaction.TRANSIT_FRAGMENT_CLOSE
        }
    }


    /*
    Adding and updating data to namaz prayer
     */
    @SuppressLint("SetTextI18n")
    private fun setPrayerData() {

        binding.txtDateToday.text = model.date.toString() + "/" + model.month.toString()+
                "/" + model.year.toString() + "   " + model.dayName

        sharedPreferences = requireContext().getSharedPreferences("checkDate", MODE_PRIVATE)
        when (sharedPreferences.getInt("Date", 0)) {
            0 -> {
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putInt("Date", model.date)
                editor.apply()
                val prayerRecord = PrayerRecord(model.yearDay, model.date, model.month, model.year, model.weekDay,
                    model.weekOfYear, false, false, false, false, false)
                model.addPrayer(prayerRecord)
            }
            model.date -> {

            }
            else -> {
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putInt("Date", model.date)
                editor.apply()
                val prayerRecord = PrayerRecord(model.yearDay,
                    model.date, model.month, model.year, model.weekDay,
                    model.weekOfYear, false, false, false, false, false)
                model.addPrayer(prayerRecord)
                setStateOn("Fajr")
                setStateOn("Dhuhur")
                setStateOn("Asr")
                setStateOn("Maghrib")
                setStateOn("Isha")
                binding.switchFajr.isChecked = false
                binding.switchFajr.isEnabled = true
                binding.switchZohr.isChecked = false
                binding.switchZohr.isEnabled = true
                binding.switchAshr.isChecked = false
                binding.switchAshr.isEnabled = true
                binding.switchMagrb.isChecked = false
                binding.switchMagrb.isEnabled = true
                binding.switchEsha.isChecked = false
                binding.switchEsha.isEnabled = true
//                Log.d("ResultOut", "New Data Added On New Date")
            }
        }
    }

    private fun setStateOff(s: String) {
        val isPrayer: Boolean = sharedPreferences.getBoolean(s, false)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        if(!isPrayer){
            editor.putBoolean(s, true)
            editor.apply()
        }
    }

    private fun setStateOn(s: String) {
        val isPrayer = sharedPreferences.getBoolean(s, false)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        if(isPrayer){
            editor.putBoolean(s, false)
            editor.apply()
        }
    }

    /*
    Showing Dialog For Code To Update Data for Respective Prayer
     */
    fun codeDialog(id: Int)
    {
        val code = 1111
        bindingDialog = CustomCodeDialogueBinding.inflate(layoutInflater)

        dialogCode = Dialog(requireContext())
        dialogCode.setContentView(bindingDialog.root)
        dialogCode.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialogCode.setCancelable(false)
        dialogCode.show()

        bindingDialog.btnCodeDone.setOnClickListener {
            if(bindingDialog.edtCode.text.isEmpty())
            {
                Toast.makeText(requireContext(), "Insert Code", Toast.LENGTH_SHORT).show()
            }
            else if(Integer.parseInt(bindingDialog.edtCode.text.toString()) == code)
            {
                val inputCode: Int = Integer.parseInt(bindingDialog.edtCode.text.toString())
                if(inputCode == code)
                {
                    Toast.makeText(requireContext(), "Password Ok", Toast.LENGTH_SHORT).show()
                    when (id) {
                        1 -> {
                            //Update Fajr Prayer
                            model.updateFajr(true, model.date)
                            setStateOff("Fajr")
                            dialogCode.dismiss()
                            binding.switchFajr.isEnabled = false
                            Log.d("ResultOut", "Fajr Successfully Updated")
                        }
                        2 -> {
                            //Update Dhuhur
                            model.updateDhuhr(true, model.date)
                            setStateOff("Dhuhur")
                            dialogCode.dismiss()
                            binding.switchZohr.isEnabled = false
                            Log.d("ResultOut", "Dhuhur Successfully Updated")
                        }
                        3 -> {
                            //Update Asr
                            model.updateAsr(true, model.date)
                            setStateOff("Asr")
                            dialogCode.dismiss()
                            binding.switchAshr.isEnabled = false
                            //Log.d("ResultOut", "Asr Successfully Updated")
                        }
                        4 -> {
                            //Update Asr
                            model.updateMaghrib(true, model.date)
                            setStateOff("Maghrib")
                            dialogCode.dismiss()
                            binding.switchMagrb.isEnabled = false
                            //Log.d("ResultOut", "Maghrib Successfully Updated")
                        }
                        5 -> {
                            //Update Esha
                            model.updateIsha(true, model.date)
                            setStateOff("Isha")
                            dialogCode.dismiss()
                            binding.switchEsha.isEnabled = false
                            //Log.d("ResultOut", "Isha Successfully Updated")
                        }
                        else -> {
                            //Do Nothing
                        }
                    }
                }
            }
            else
            {
                Toast.makeText(requireContext(), "Password Does Not Match", Toast.LENGTH_SHORT).show()
                when (id) {
                    1 -> {
                        binding.switchFajr.isChecked = false
                    }
                    2 -> {
                        binding.switchZohr.isChecked = false
                    }
                    3 -> {
                        binding.switchAshr.isChecked = false
                    }
                    4 -> {
                        binding.switchMagrb.isChecked = false
                    }
                }
                dialogCode.dismiss()
            }
        }

        bindingDialog.btnCodeCancel.setOnClickListener {
            dialogCode.dismiss()
        }
    }


    /*
    Checking Prayers Switch States
     */

    fun checkSwitchState() {
        val isFajr: Boolean = sharedPreferences.getBoolean("Fajr", false)
        val isDhuhur: Boolean = sharedPreferences.getBoolean("Dhuhur", false)
        val isAsr: Boolean = sharedPreferences.getBoolean("Asr", false)
        val isMaghrib: Boolean = sharedPreferences.getBoolean("Maghrib", false)
        val isIsha: Boolean = sharedPreferences.getBoolean("Isha", false)
        if(isFajr){
            binding.switchFajr.isChecked = true
            binding.switchFajr.isEnabled = false
        }
        if(isDhuhur){
            binding.switchZohr.isChecked = true
            binding.switchZohr.isEnabled = false
        }
        if(isAsr){
            binding.switchAshr.isChecked = true
            binding.switchAshr.isEnabled = false
        }
        if(isMaghrib){
            binding.switchMagrb.isChecked = true
            binding.switchMagrb.isEnabled = false
        }
        if(isIsha){
            binding.switchEsha.isChecked = true
            binding.switchEsha.isEnabled = false
        }

    }
}
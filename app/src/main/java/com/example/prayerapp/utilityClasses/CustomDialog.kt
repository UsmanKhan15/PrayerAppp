package com.example.prayerapp.utilityClasses

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.prayerapp.R

class CustomDialog {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var dialog: Dialog

    /*
            Dialog Function For Getting Name
             */
    fun dialog(context: Context)
    {
        sharedPreferences = context.getSharedPreferences("onFirstTime",
            Context.MODE_PRIVATE
        )
        val isFirst: String? = sharedPreferences.getString("first", null)
        if(isFirst.equals(null)){
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            Toast.makeText(context, "First Time Enter your name", Toast.LENGTH_SHORT).show()

            //Creating dialog
            dialog = Dialog(context)
            dialog.setContentView(R.layout.custom_dialogue)
            dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.show()
            val btnDone: Button = dialog.findViewById(R.id.btnDone)
            val edtName: EditText = dialog.findViewById(R.id.edtName)
            btnDone.setOnClickListener {
                val name = edtName.text.toString()
                Toast.makeText(context, "Name: " + name, Toast.LENGTH_SHORT).show()
                if(name.isNotEmpty())
                {
                    editor.putString("first", name)
                    editor.apply()
                    dialog.dismiss()
                }
                else
                {
                    editor.putString("first", null)
                    editor.apply()
                }
            }
        }
    }
}
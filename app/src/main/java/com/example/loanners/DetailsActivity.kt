package com.example.loanners

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    val educationName = arrayOf("Tunku Abdul Rahman University College","University of Cambridge","University of Malaya","University Tunku Abdul Rahman","Monash University")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        var adapter = ArrayAdapter<String>(this, android.R.layout.select_dialog_item, educationName)
        val activity = autoEducation
        activity.threshold = 5//will start working from first character
        activity.setAdapter(adapter)//setting the adapter data into the AutoCompleteTextView

        buttonDone.setOnClickListener {
            validData()
        }
    }

    private fun validData() {
        val fullName = editTextFullName.text.toString()
        val gender  = findViewById<RadioButton>(radioGender.checkedRadioButtonId).text.toString()

        val icNumber = editTextIC.text.toString()
        val icRegex = "/^\\d{6}-\\d{2}-\\d{4}\$/".toRegex()

        val phoneNumber = editTextPhoneNumber.text.toString()
        val phoneRegex = "^(01)[0-46-9]*[0-9]{7,8}\$".toRegex()

        val address = editTextStreet1.text.toString() + editTextStreet2.text.toString() + editTextCity.text.toString() + editTextState.text.toString() + editTextPost.text.toString()
        val education = autoEducation.text.toString()
        val result = educationName.binarySearch(education,0)

        if(result <= 0){
            val index = educationName.lastIndex
            educationName[index + 1] = education
        }

        if(!icNumber.matches(icRegex)){
            Toast.makeText(this,"IC number didn't match format.", Toast.LENGTH_SHORT).show()
        }
        else if(!phoneNumber.matches(phoneRegex)){
            Toast.makeText(this,"Phone number didn't match format.",Toast.LENGTH_SHORT).show()
        }

    }

}

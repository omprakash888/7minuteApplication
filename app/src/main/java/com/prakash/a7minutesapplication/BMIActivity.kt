package com.prakash.a7minutesapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.prakash.a7minutesapplication.databinding.ActivityBmiBinding

class BMIActivity : AppCompatActivity() {


    private var binding : ActivityBmiBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolBarBmiActivity)

       if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
           supportActionBar?.title = "CALCULATE BMI"
       }

        binding?.toolBarBmiActivity?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding?.calculateButton?.setOnClickListener {
            if(validateMetricUnits()) {
                val heightValue : Float = binding?.etMetricUnitHeight?.text.toString().toFloat()/100
                val weightValue : Float = binding?.etMetricUnitWeight?.text.toString().toFloat()

                val bmi = weightValue/heightValue

                binding?.tvYourBmiValue?.text = bmi.toString()
                displayBMIResult(bmi)
            }
            else {
                Toast.makeText(this,"Enter Correct Values", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun displayBMIResult(bmi : Float) {
        val bmiType : String
        val bmiDescription : String


        if(bmi.compareTo(15f) <= 0) {
            bmiType = "very Severely underweight"
            bmiDescription = "Oops, you really need to take better care of yourself"
        }
        else if(bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
            bmiType = "underweight"
            bmiDescription = "Oops, you really need to take better care of yourself"
        }
        else if(bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0) {
            bmiType = "Normal"
            bmiDescription = "Congratulations, You are in a good shape!"
        }
        else {
            bmiType = "Overweight"
            bmiDescription = "Reduce your weight"
        }

        binding?.tvYourBmiType?.text = bmiType
        binding?.bmiDescription?.text = bmiDescription
        binding?.llBmi?.visibility = View.VISIBLE

    }

    private fun validateMetricUnits() : Boolean {
        var isValid = true
        if(binding?.etMetricUnitHeight?.text.toString().isEmpty()) {
            isValid = false
        }
        else if(binding?.etMetricUnitHeight?.text.toString().isEmpty()) {
            isValid = false
        }
        return isValid
    }
 }
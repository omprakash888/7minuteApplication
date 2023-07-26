package com.prakash.a7minutesapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.prakash.a7minutesapplication.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {

    private  var binding : ActivityExerciseBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarExercise)

        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
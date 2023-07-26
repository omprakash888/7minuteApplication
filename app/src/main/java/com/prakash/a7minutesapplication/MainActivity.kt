package com.prakash.a7minutesapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val flStartButton : FrameLayout = findViewById(R.id.start)
        flStartButton.setOnClickListener {
            Toast.makeText(this@MainActivity,
                "Here we go", Toast.LENGTH_LONG).show()
        }
    }
}
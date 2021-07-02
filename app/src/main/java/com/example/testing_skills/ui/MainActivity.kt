package com.example.testing_skills.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.testing_skills.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var internValidation : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        internValidation = btInternValidation

        internValidation.setOnClickListener {
            val choosedOne = Intent(this, ActLogin::class.java)
            startActivity(choosedOne)
        }
    }
}
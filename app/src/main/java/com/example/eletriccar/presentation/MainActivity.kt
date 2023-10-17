package com.example.eletriccar.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.eletriccar.R

class MainActivity : AppCompatActivity() {

    lateinit var btnCalculate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
        setupListeners()
    }

    fun setupView() {
        btnCalculate = findViewById(R.id.btn_redirect)
    }

    fun setupListeners() {
        btnCalculate.setOnClickListener {
            startActivity(Intent(this, AutonomyCalculateActivity::class.java))
        }
    }

}
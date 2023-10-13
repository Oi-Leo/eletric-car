package com.example.eletriccar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var price: EditText
    lateinit var mileage: EditText
    lateinit var btnCalculate: Button
    lateinit var results: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
        setupListeners()
    }

    fun setupView() {
        results = findViewById(R.id.tv_result)
        mileage = findViewById(R.id.et_mileage)
        price = findViewById(R.id.et_price_kwh)
        btnCalculate = findViewById(R.id.btn_calculate)
    }

    fun setupListeners() {
        btnCalculate.setOnClickListener {
            calculate()
        }
    }

    fun calculate() {
        val price = price.text.toString().toFloat()
        val km = mileage.text.toString().toFloat()
        val result = price / km

        results.text = result.toString()
    }

}
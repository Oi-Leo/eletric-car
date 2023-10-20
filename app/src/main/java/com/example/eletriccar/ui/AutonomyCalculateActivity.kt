package com.example.eletriccar.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.eletriccar.R

class AutonomyCalculateActivity : AppCompatActivity() {

    lateinit var price: EditText
    lateinit var results: TextView
    lateinit var mileage: EditText
    lateinit var btnCalculate: Button
    lateinit var btnClose: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autonomy_calculate)
        setupView()
        setupListeners()
    }

    fun setupView() {
        results = findViewById(R.id.tv_result)
        mileage = findViewById(R.id.et_mileage)
        price = findViewById(R.id.et_price_kwh)
        btnCalculate = findViewById(R.id.btn_calculate)
        btnClose = findViewById(R.id.iv_close)
    }

    fun setupListeners() {
        btnCalculate.setOnClickListener {
            calculate()
        }
        btnClose.setOnClickListener {
            finish()
        }
    }

    fun calculate() {
        val price = price.text.toString().toFloat()
        val km = mileage.text.toString().toFloat()
        val result = price / km

        results.text = result.toString()
    }

}
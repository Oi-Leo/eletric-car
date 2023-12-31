package com.example.eletriccar.ui

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.eletriccar.R
import java.net.HttpURLConnection
import java.net.URL

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
        setupCachedResult()
    }

    private fun setupCachedResult() {
        val calculateValue = getSharedPref()
        results.text = calculateValue.toString()
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
        saveSharedPref(result)
    }

    fun saveSharedPref(value: Float) {
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putFloat(getString(R.string.saved_calc), value)
            apply()
        }
    }

    fun getSharedPref(): Float {
        val sharedPref = getPreferences(
            Context.MODE_PRIVATE
        )
        return sharedPref.getFloat(
            getString(
                R.string.saved_calc
            ), 0.0f
        )
    }
}
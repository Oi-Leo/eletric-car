package com.example.eletriccar.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.eletriccar.R
import com.example.eletriccar.data.CarFactory
import com.example.eletriccar.ui.adapter.CarAdapter

class MainActivity : AppCompatActivity() {

    lateinit var btnCalculate: Button
    lateinit var carList: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
        setupListeners()
        setupList()
    }

    fun setupView() {
        btnCalculate = findViewById(R.id.btn_calculate)
        carList = findViewById(R.id.rv_list)
    }

    fun setupList() {
        val adapter = CarAdapter(CarFactory.list)
        carList.adapter = adapter
    }

    fun setupListeners() {
        btnCalculate.setOnClickListener {
            startActivity(Intent(this, AutonomyCalculateActivity::class.java))
        }
    }

}
package com.example.eletriccar.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.eletriccar.R
import com.example.eletriccar.data.CarFactory
import com.example.eletriccar.ui.adapter.CarAdapter

class CarFragment : Fragment() {

    lateinit var btnCalculate: Button
    lateinit var carList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.car_fragment,
            container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupList()
    }

    fun setupView(view: View) {
        view.apply {
            btnCalculate = findViewById(R.id.btn_calculate)
            carList = findViewById(R.id.rv_list)
        }
    }

    fun setupList() {
        val adapter = CarAdapter(CarFactory.list)
        carList.adapter = adapter
    }

    fun setupListeners() {
        btnCalculate.setOnClickListener {
            //  startActivity(Intent(this, AutonomyCalculateActivity::class.java))
        }

    }
}
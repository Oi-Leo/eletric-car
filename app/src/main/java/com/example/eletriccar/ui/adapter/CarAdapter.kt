package com.example.eletriccar.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eletriccar.R
import com.example.eletriccar.domain.Car

class CarAdapter(private val cars: List<Car>) : RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val price: TextView
        val battery: TextView
        val power: TextView
        val refill: TextView

        init {
            view.apply {
                price = findViewById(R.id.tv_value_price)
                battery = findViewById(R.id.tv_value_battery)
                power = findViewById(R.id.tv_value_power)
                refill = findViewById(R.id.tv_value_refill)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.car_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = cars.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.price.text = cars[position].price
        holder.battery.text = cars[position].battery
        holder.power.text = cars[position].power
        holder.refill.text = cars[position].refill
    }
}
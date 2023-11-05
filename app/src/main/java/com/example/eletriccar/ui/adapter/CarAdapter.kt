package com.example.eletriccar.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eletriccar.R
import com.example.eletriccar.domain.Car

class CarAdapter(private val cars: List<Car>) :
    RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    var carItemLister: (Car) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.car_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.price.text = cars[position].price
        holder.battery.text = cars[position].battery
        holder.power.text = cars[position].power
        holder.refill.text = cars[position].refill
        holder.favorite.setOnClickListener {
            val car = cars[position]
            carItemLister(car)
            setupFavorite(car, holder)
        }
    }

    private fun setupFavorite(
        car: Car,
        holder: ViewHolder
    ) {
        car.isFavorite = !car.isFavorite

        if (car.isFavorite)
            holder.favorite.setImageResource(R.drawable.ic_selected_favorite)
        else
            holder.favorite.setImageResource(R.drawable.ic_favorite)
    }

    override fun getItemCount(): Int = cars.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val price: TextView
        val battery: TextView
        val power: TextView
        val refill: TextView
        val favorite: ImageView

        init {
            view.apply {
                price = findViewById(R.id.tv_value_price)
                battery = findViewById(R.id.tv_value_battery)
                power = findViewById(R.id.tv_value_power)
                refill = findViewById(R.id.tv_value_refill)
                favorite = findViewById(R.id.iv_favorite)
            }
        }
    }
}
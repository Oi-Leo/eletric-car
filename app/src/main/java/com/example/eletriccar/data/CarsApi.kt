package com.example.eletriccar.data

import com.example.eletriccar.domain.Car
import retrofit2.http.GET

interface CarsApi {


    @GET("cars.json")
    fun getAllCars() : retrofit2.Call<List<Car>>

}
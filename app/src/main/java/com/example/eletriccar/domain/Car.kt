package com.example.eletriccar.domain

data class Car(
    val id: Int,
    val price: String,
    val battery: String,
    val power: String,
    val urlPhoto: String,
    val refill: String,
    var isFavorite: Boolean
)
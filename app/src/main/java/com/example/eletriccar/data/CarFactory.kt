package com.example.eletriccar.data

import com.example.eletriccar.domain.Car

object CarFactory {

    val list = listOf(
        Car(
            id = 1,
            price = "R$ 250.900,90",
            battery = "300 kWh",
            power = "200cv",
            refill = "42 Min",
            urlPhoto = "www.google.com.br"
        ),
        Car(
            id = 2,
            price = "R$ 50.900,90",
            battery = "2400 kWh",
            power = "57cv",
            refill = "30 Min",
            urlPhoto = "www.google.com.br"
        ),
        Car(
            id = 3,
            price = "R$ 7.850.900,90",
            battery = "490 kWh",
            power = "789cv",
            refill = "12 Hours",
            urlPhoto = "www.google.com.br"
        )
    )
}
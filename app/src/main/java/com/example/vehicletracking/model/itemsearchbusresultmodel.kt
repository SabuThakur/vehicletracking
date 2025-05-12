package com.example.vehicletracking.model

data class itemsearchbusresultmodel(
    val rating: String,
    val promo: String,
    val name: String,
    val type: String,
    val timing: String,
    var fare: Int,
    var originalFare: Int,
    val seatsLeft: String,
    val isElectric: Boolean=false,
    val amenities: String,
    val departure: String,
    val arrival: String,
    var isAC: Boolean = false,
    var isSeater: Boolean = true,
)





package com.example.vehicletracking.model
data class routeoptionmodel(
    val name: String,
    val amenities: String,
    val departure: String,
    val arrival: String,
    val cancellationPolicy: String,
    val imageResId: Int, // You can use a drawable resource or switch to URL with Glide
    val baseFare: Double,
    val femaleFare: Double,
    val kidFare: Double,
    val seniorFare: Double
)





package com.example.vehicletracking.model

data  class upcomingbusmodel (
        val busImage: Int,
        val busName: String,
        val journeyDate: String,
        val busRoute: String,
        val departureTime: String,
        val amenities: String = "WiFi, Charging",
        val cancellationPolicy: String = "Free till 1 hour before",
        val fare: Int = 599
    )

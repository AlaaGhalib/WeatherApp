package com.example.myapplication.model

data class WeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val hourly: Hourly?
)

data class Hourly(
    val time: List<String>?,
    val temperature_2m: List<Double>?,
    val cloudCover: List<Int>?
)

data class HourlyData(
    val time: String,
    val temperature: Double,
    val cloudCover: Int
)

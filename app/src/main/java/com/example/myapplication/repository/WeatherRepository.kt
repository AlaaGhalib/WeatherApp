package com.example.myapplication.repository

import com.example.myapplication.api.ApiClient
import com.example.myapplication.api.WeatherService
import com.example.myapplication.model.WeatherResponse
import retrofit2.Call

object WeatherRepository {
    private val weatherService: WeatherService = ApiClient.retrofitInstance!!.create(WeatherService::class.java)

    fun getWeatherForecast(latitude: Double, longitude: Double): Call<WeatherResponse> {
        return weatherService.getWeatherForecast(
            latitude = latitude,
            longitude = longitude,
            hourly = "temperature_2m,cloud_cover"
        )
    }
}

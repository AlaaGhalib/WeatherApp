package com.example.myapplication.parser

import com.example.myapplication.model.WeatherResponse
import com.google.gson.Gson

class WeatherParser {
    fun parseWeatherResponse(jsonString: String): WeatherResponse? {
        return try {
            Gson().fromJson(jsonString, WeatherResponse::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

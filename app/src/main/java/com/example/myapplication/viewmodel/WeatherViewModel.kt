package com.example.myapplication.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.HourlyData
import com.example.myapplication.model.WeatherResponse
import com.example.myapplication.repository.WeatherRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val _weatherData = MutableLiveData<List<HourlyData>>()
    val weatherData: LiveData<List<HourlyData>> get() = _weatherData

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val sharedPreferences: SharedPreferences =
        application.getSharedPreferences("WeatherPrefs", Context.MODE_PRIVATE)

    private val gson = Gson()

    init {
        loadWeatherDataFromPrefs()
    }

    // Function to request weather data from API and save to SharedPreferences
    fun getWeatherData(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val call = WeatherRepository.getWeatherForecast(latitude, longitude)
            call.enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        val hourly = response.body()!!.hourly
                        if (hourly != null) {
                            val data = hourly.time?.mapIndexed { index, time ->
                                HourlyData(
                                    time = time,
                                    temperature = hourly.temperature_2m?.get(index) ?: 0.0,
                                    cloud_cover = hourly.cloud_cover?.get(index) ?: 0
                                )
                            } ?: emptyList()
                            _weatherData.value = data
                            saveWeatherDataToPrefs(data)
                        }
                    } else {
                        _errorMessage.value = "No internet connection: Fetching cashed data"
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    _errorMessage.value = "No internet connection: Fetching cashed data"
                }
            })
        }
    }

    // Save weather data to SharedPreferences
    private fun saveWeatherDataToPrefs(data: List<HourlyData>) {
        val editor = sharedPreferences.edit()
        val json = gson.toJson(data)
        editor.putString("weather_data", json)
        editor.apply()
    }

    // Load weather data from SharedPreferences
    private fun loadWeatherDataFromPrefs() {
        val json = sharedPreferences.getString("weather_data", null)
        if (json != null) {
            val type = object : TypeToken<List<HourlyData>>() {}.type
            val data: List<HourlyData> = gson.fromJson(json, type)
            _weatherData.value = data
        }
    }
}

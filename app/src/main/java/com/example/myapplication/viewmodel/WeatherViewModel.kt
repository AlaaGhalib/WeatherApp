package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.HourlyData
import com.example.myapplication.model.WeatherResponse
import com.example.myapplication.repository.WeatherRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel : ViewModel() {

    private val _weatherData = MutableLiveData<List<HourlyData>>()
    val weatherData: LiveData<List<HourlyData>> get() = _weatherData

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    // Function to request weather data
    fun getWeatherData(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val call = WeatherRepository.getWeatherForecast(latitude, longitude)
            call.enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        val hourly = response.body()!!.hourly
                        if (hourly != null) {
                            _weatherData.value = hourly.time?.mapIndexed { index, time ->
                                HourlyData(
                                    time = time,
                                    temperature = hourly.temperature_2m?.get(index) ?: 0.0,
                                    cloud_cover = hourly.cloud_cover?.get(index) ?: 0
                                )
                            } ?: emptyList()
                        }
                    } else {
                        _errorMessage.value = "Failed to load data: ${response.message()}"
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    _errorMessage.value = "Failed to load data: ${t.message}"
                }
            })
        }
    }
}

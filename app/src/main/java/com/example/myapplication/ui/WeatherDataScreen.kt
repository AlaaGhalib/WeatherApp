import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items // Correct import to use items() in LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import com.example.myapplication.api.ApiClient
import com.example.myapplication.api.WeatherService
import com.example.myapplication.model.HourlyData
import com.example.myapplication.model.WeatherResponse
import com.example.myapplication.ui.themes.WeatherDataItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun WeatherDataScreen(latitude: Float, longitude: Float) {
    val weatherService = ApiClient.retrofitInstance!!.create(WeatherService::class.java)
    var weatherData by remember { mutableStateOf<List<HourlyData>>(emptyList()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Load data asynchronously
    LaunchedEffect(latitude, longitude) {
        val call = weatherService.getWeatherForecast(
            latitude.toDouble(),
            longitude.toDouble(),
            "temperature_2m,cloud_cover"
        )
        call.enqueue(object : Callback<WeatherResponse?> {
            override fun onResponse(call: Call<WeatherResponse?>, response: Response<WeatherResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    val hourly = response.body()!!.hourly
                    if (hourly != null) {
                        weatherData = hourly.time?.mapIndexed { index, time ->
                            HourlyData(
                                time = time,
                                temperature = hourly.temperature_2m?.get(index) ?: 0.0,
                                cloud_cover = hourly.cloud_cover?.get(index) ?:0,
                            )
                        } ?: emptyList()
                    }
                } else {
                    errorMessage = "Failed to load data: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<WeatherResponse?>, t: Throwable) {
                errorMessage = "Failed to load data: ${t.message}"
            }
        })
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        errorMessage?.let {
            Text(text = it, color = Color.Red)
        }
        LazyColumn {
            items(weatherData) { data ->
                WeatherDataItem(data)
            }
        }
    }
}

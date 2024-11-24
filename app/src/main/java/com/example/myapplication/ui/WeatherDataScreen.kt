import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.model.HourlyData
import com.example.myapplication.ui.themes.WeatherDataItem
import com.example.myapplication.viewmodel.WeatherViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState


@Composable
fun WeatherDataScreen(latitude: Float, longitude: Float) {
    // Instantiate ViewModel using the ViewModelProvider
    val weatherViewModel: WeatherViewModel = viewModel()

    // Trigger data loading when the composable is first loaded
    LaunchedEffect(latitude, longitude) {
        weatherViewModel.getWeatherData(latitude.toDouble(), longitude.toDouble())
    }

    // Observe LiveData from ViewModel
    val weatherData by weatherViewModel.weatherData.observeAsState(initial = emptyList())
    val errorMessage by weatherViewModel.errorMessage.observeAsState(initial = null)

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Display the error message if present
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
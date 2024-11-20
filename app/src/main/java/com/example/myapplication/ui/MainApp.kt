import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.myapplication.ui.StartScreen

@Composable
fun MainApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "start_screen") {
        composable("start_screen") {
            StartScreen { latitude, longitude ->
                navController.navigate("weather_data_screen/$latitude/$longitude")
            }
        }
        composable(
            "weather_data_screen/{latitude}/{longitude}",
            arguments = listOf(
                navArgument("latitude") { type = NavType.FloatType },
                navArgument("longitude") { type = NavType.FloatType }
            )
        ) { backStackEntry ->
            val latitude = backStackEntry.arguments?.getFloat("latitude") ?: 0.0f
            val longitude = backStackEntry.arguments?.getFloat("longitude") ?: 0.0f
            WeatherDataScreen(latitude = latitude, longitude = longitude)
        }
    }
}

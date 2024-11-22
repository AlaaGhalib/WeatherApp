package com.example.myapplication.ui.themes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import com.example.myapplication.R
import com.example.myapplication.model.HourlyData

@Composable
fun WeatherDataItem(data: HourlyData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = "Time: ${data.time}")
        Text(text = "Temperature: ${data.temperature} °C")
        Text(text = "Cloud Cover: ${data.cloud_cover}%")
        CloudCoverImage(cloudCover = data.cloud_cover)
    }
}

@Composable
fun CloudCoverImage(cloudCover: Int) {
    // Select appropriate drawable resource based on cloudCover value
    val cloudImage: Painter = when {
        cloudCover in 0..50 -> painterResource(id = R.drawable.cloud50_0)
        cloudCover in 51..80 -> painterResource(id = R.drawable.cloud80_50)
        cloudCover in 81..100 -> painterResource(id = R.drawable.cloud100_80)
        else -> painterResource(id = R.drawable.cloud100_80) // Default fallback
    }

    // Display the selected image
    Image(
        painter = cloudImage,
        contentDescription = "Cloud cover $cloudCover%",
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}

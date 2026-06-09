package com.example.openweatherapp.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.openweatherapp.viewmodel.WeatherViewModel
@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    var city by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        // 🔍 Search box (not at top, nicely spaced)
        Text(
            text = "Weather App",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = city,
            onValueChange = { city = it },
            placeholder = { Text("Enter city") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                if (city.isNotBlank()) {
                    viewModel.search(city)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 🌤️ STATE UI
        val s = state

        Column {

            if (s.isOffline) {
                Text("You are offline", color = Color.Red)
            }

            if (s.isLoading) {
                CircularProgressIndicator()
            }

            s.error?.let {
                Text(text = it, color = Color.Red)
            }

            s.weather?.let { weather ->

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = weather.city,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(10.dp))

                AsyncImage(
                    model = weather.iconUrl,
                    contentDescription = "Weather icon",
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text("${weather.temperature}°C")
                Text(weather.description)
            }

            if (!s.isLoading && s.weather == null && s.error == null) {
                Text("Search a city to get weather")
            }
        }
    }
}
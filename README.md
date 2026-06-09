# 🌤️ OpenWeather Android App

A modern Android weather application built using **Kotlin**, **Jetpack Compose**, and **MVVM architecture**.  
It allows users to search for a city, country or city, state and country for USA only and view real-time weather information using the OpenWeatherMap API.

---

## ✨ Features

- 🔍 Search weather by city, country or city, state and country for USA only or city only
- 🌤️ Displays temperature, description, humidity, and weather icon
- 🌐 Real-time API integration (OpenWeatherMap)
- 📶 Offline state detection (Connectivity Observer)
- 💾 Saved last searched city (SavedStateHandle) and storing it in Room DB
- 🔄 Clean MVVM architecture
- ⚡ Kotlin Coroutines + Flow for async handling
- 🧪 Unit tested ViewModel logic (JUnit + Mockito)

---

## 🧱 Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose
- **Architecture:** MVVM + Clean Architecture
- **Networking:** Retrofit + OkHttp
- **Dependency Injection:** Hilt
- **Asynchronous:** Kotlin Coroutines + Flow
- **Local State:** SavedStateHandle
- **Testing:** JUnit, Mockito, kotlinx-coroutines-test
- **Image Loading:** Coil

---

## 🏗️ Architecture

UI (Compose) -> ViewModel -> UseCase -> Repository -> Remote Data Source (Retrofit)

---

## 🌐 API Used

OpenWeatherMap API:
https://api.openweathermap.org/data/2.5/weather

Weather Icon:
https://openweathermap.org/img/wn/{icon}@2x.png


---

## 📱 Screens

### Weather Search Screen
- Search bar for city input
- Weather result card
- Loading indicator
- Error handling
- Offline indicator

---

## 🚀 How to Run

1. Clone the repository
2. Open in Android Studio
3. Add your API key in: 
object ApiKeys {
    const val OPEN_WEATHER_KEY = "YOUR_API_KEY"
}
4. Run the app on emulator or device

🧪 Testing

Unit tests cover:

1. Successful weather fetch
2. Network failure handling
3. Timeout handling
4. Connectivity state updates

Run tests: ./gradlew test

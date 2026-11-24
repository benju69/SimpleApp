package fr.benju.simpleapp.domain

interface WeatherRepository {
    suspend fun getCurrentWeather(): Result<CurrentWeather>
}

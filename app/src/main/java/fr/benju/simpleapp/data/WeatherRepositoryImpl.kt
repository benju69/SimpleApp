package fr.benju.simpleapp.data

import fr.benju.simpleapp.domain.CurrentWeather
import fr.benju.simpleapp.domain.WeatherRepository
import fr.benju.simpleapp.network.WeatherApi
import fr.benju.simpleapp.network.model.toDomain
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    val api: WeatherApi
) : WeatherRepository {
    override suspend fun getCurrentWeather(): Result<CurrentWeather> {
        return try {
            val currentWeatherDto = api.getCurrentWeather()
            Result.success(currentWeatherDto.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

package fr.benju.simpleapp.network

import fr.benju.simpleapp.network.model.CurrentWeatherDto
import retrofit2.http.GET

const val BASE_URL = "https://api.weatherapi.com/"
const val API_KEY = "43f20d735fc94ad1a5c122951252008"

interface WeatherApi {

    @GET("v1/current.json?key=${API_KEY}&q=London&aqi=no")
    suspend fun getCurrentWeather(): CurrentWeatherDto

}
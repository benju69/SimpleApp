package fr.benju.simpleapp.domain

data class CurrentWeather (
    val locationName : String,
    val temperature : Double,
    val condition : String
)
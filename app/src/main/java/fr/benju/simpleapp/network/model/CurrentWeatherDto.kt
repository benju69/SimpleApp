package fr.benju.simpleapp.network.model


import fr.benju.simpleapp.domain.CurrentWeather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherDto(
    @SerialName("current")
    val current: Current,
    @SerialName("location")
    val location: Location
)

fun CurrentWeatherDto.toDomain() = CurrentWeather(
    locationName = location.name,
    temperature = current.tempC,
    condition = current.condition.text
)
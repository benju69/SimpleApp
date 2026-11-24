package fr.benju.simpleapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.benju.simpleapp.data.WeatherRepositoryImpl
import fr.benju.simpleapp.domain.CurrentWeather
import fr.benju.simpleapp.domain.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface UiState{
    data object LoadingState: UiState
    data class Success(val currentWeather: CurrentWeather) : UiState
    data class ErrorState(val errorMessage: String): UiState
}

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    private val _state = MutableStateFlow<UiState>(UiState.LoadingState)
    val state : StateFlow<UiState> = _state.asStateFlow()

    init {
        load()
    }

    fun load() {
        _state.value = UiState.LoadingState
        viewModelScope.launch {
//            try {
//                val currentWeather = weatherRepository.getCurrentWeather()
//                _state.value = UiState.Success(currentWeather)
//            } catch (e: Exception) {
//                _state.value = UiState.ErrorState(e.message ?: "An error occurred")
//            }
            _state.value = weatherRepository.getCurrentWeather().fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.ErrorState(it.message ?: "An error occurred") }
            )
        }
    }
}
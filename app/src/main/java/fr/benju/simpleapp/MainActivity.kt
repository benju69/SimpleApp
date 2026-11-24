package fr.benju.simpleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import fr.benju.simpleapp.ui.home.UiState
import fr.benju.simpleapp.ui.home.WeatherViewModel
import fr.benju.simpleapp.ui.theme.SimpleAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleAppTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Simple Weather App") }
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    WeatherScreen(innerPadding)
                }
            }
        }
    }
}

@Composable
fun WeatherScreen(innerPadding: PaddingValues, viewModel: WeatherViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    when (state) {
        is UiState.LoadingState -> {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxHeight()
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        is UiState.ErrorState -> {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxHeight()
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("error: ${(state as UiState.ErrorState).errorMessage}")
            }
        }

        is UiState.Success -> {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .fillMaxHeight(),
            ) {
                Text(text = "Temperature: ${(state as UiState.Success).currentWeather.temperature} °C")

                Spacer(modifier = Modifier.padding(8.dp))

                Text(text = "Condition: ${(state as UiState.Success).currentWeather.condition}")

                Spacer(modifier = Modifier.padding(8.dp))

                Text(text = "Location: ${(state as UiState.Success).currentWeather.locationName}")
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SimpleAppTheme {
        Greeting("Android")
    }
}
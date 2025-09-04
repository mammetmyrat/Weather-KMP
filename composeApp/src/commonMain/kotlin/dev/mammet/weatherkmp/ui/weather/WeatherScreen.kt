package dev.mammet.weatherkmp.ui.weather

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import dev.mammet.weatherkmp.ui.components.FlagImage
import dev.mammet.weatherkmp.ui.components.LineGraph
import dev.mammet.weatherkmp.utils.Util
import dev.mammet.weatherkmp.weather.domain.models.CurrentWeather
import dev.mammet.weatherkmp.weather.domain.models.Daily
import dev.mammet.weatherkmp.weather.domain.models.Hourly
import kotlinx.datetime.Clock
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

const val degreeTxt = "\u00B0C"

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    weatherViewModel: WeatherViewModel = koinViewModel(),
    onSearchClick: () -> Unit,
) {
    val weatherState by weatherViewModel.weatherState.collectAsStateWithLifecycle()

    LaunchedEffect(weatherViewModel.weatherState.value.selectedLocation) {
        weatherViewModel.fetchWeatherData()
    }

    Box(
        modifier = modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            if (weatherState.error != null) {
                Text(weatherState.error ?: "Unknown error")
            }
            when (weatherState.isLoading) {
                true -> CircularProgressIndicator()
                else -> {
                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        item {
                            weatherState.weather?.let { notnullWeather ->
                                CurrentWeatherItem(
                                    currentWeather = notnullWeather.currentWeather,
                                )
                                weatherState.dailyWeatherInfo?.let { dailyWeatherInfo ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 16.dp),
                                    ) {
                                        SunsetWeatherItem(weatherInfo = dailyWeatherInfo)
                                        Spacer(modifier = Modifier.weight(1f))
                                        UvIndexWeatherItem(weatherInfo = dailyWeatherInfo)
                                    }
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                HourlyWeatherItem(
                                    hourly = notnullWeather.hourly
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                        item {
                            weatherState.weather?.let { weather ->
                                LineGraph(
                                    dataPoints = weather.hourly.weatherInfo,
                                    xValueMapper = { it.time.take(2) },
                                    yValueMapper = { it.temperature.toFloat() },
                                    modifier = Modifier.fillMaxWidth()
                                        .height(250.dp),
                                    graphTitle = "Temperature Over Time"
                                )

                            }
                        }
                    }
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.TopEnd)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = ripple(),
                    onClick = onSearchClick
                )
        ) {
            weatherState.selectedLocation?.let {
                val imageRequest = ImageRequest.Builder(context = LocalPlatformContext.current)
                    .data(it.flagUrl)
                    .build()
                FlagImage(imageRequest = imageRequest, modifier = Modifier.size(24.dp))
                Spacer(Modifier.width(4.dp))
                Text(text = it.name, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }


}

@Composable
fun CurrentWeatherItem(
    modifier: Modifier = Modifier,
    currentWeather: CurrentWeather,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(currentWeather.weatherStatus.icon),
            contentDescription = null,
            modifier = Modifier.size(120.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = currentWeather.temperature.toString() + degreeTxt,
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Weather Status:${currentWeather.weatherStatus.info}",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Wind speed:${currentWeather.windSpeed} Km/h ${currentWeather.windDirection}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

    }
}

@Composable
fun SunsetWeatherItem(
    modifier: Modifier = Modifier,
    weatherInfo: Daily.WeatherInfo,
) {
    Card(
        modifier = modifier.padding(horizontal = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Sunrise",
                style = MaterialTheme.typography.headlineSmall,
            )
            Text(
                text = weatherInfo.sunrise,
                style = MaterialTheme.typography.displayMedium,
            )
            Text(
                text = "Sunset ${weatherInfo.sunset}",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
fun UvIndexWeatherItem(
    modifier: Modifier = Modifier,
    weatherInfo: Daily.WeatherInfo,
) {
    Card(
        modifier = modifier.padding(horizontal = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "UV INDEX",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.basicMarquee()
            )
            Text(
                text = weatherInfo.uvIndex.toString(),
                style = MaterialTheme.typography.displayMedium,
            )
            Text(
                text = weatherInfo.weatherStatus.info,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.basicMarquee()
            )
        }
    }
}

@Composable
fun HourlyWeatherItem(
    modifier: Modifier = Modifier,
    hourly: Hourly,
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Today",
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = Util.formatUnixToCustom(Clock.System.now().toEpochMilliseconds() / 1000),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        HorizontalDivider(
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        LazyRow(
            modifier = Modifier
                .padding(16.dp)
        ) {
            items(hourly.weatherInfo) { infoItem ->
                HourlyWeatherInfoItem(infoItem = infoItem)
            }
        }
    }
}

@Composable
fun HourlyWeatherInfoItem(
    modifier: Modifier = Modifier,
    infoItem: Hourly.HourlyInfoItem,
) {
    Column(
        modifier = modifier
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "${infoItem.temperature} $degreeTxt",
            style = MaterialTheme.typography.bodySmall,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Icon(
            painter = painterResource(resource = infoItem.weatherStatus.icon),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.height(8.dp))
        println("Time $infoItem")
        Text(
            text = infoItem.time,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}
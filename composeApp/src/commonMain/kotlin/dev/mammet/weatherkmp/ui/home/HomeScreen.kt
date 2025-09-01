package dev.mammet.weatherkmp.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.LocalPlatformContext
import dev.mammet.weatherkmp.ui.components.NavigationType
import dev.mammet.weatherkmp.ui.components.SearchLocationContent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigationType: NavigationType,
    homeViewModel: HomeViewModel = koinViewModel()
) {
    val state by homeViewModel.homeState.collectAsStateWithLifecycle()
    val (search, onSearchChange) = remember { mutableStateOf("") }
    val context = LocalPlatformContext.current
    var showSearchLocation  by rememberSaveable{mutableStateOf(false)}
    SearchLocationContent(
        modifier = modifier,
        navigationType = navigationType,
        state = state,
        search = search,
        onSearchChange = onSearchChange,
        onFavoriteClick = {geolocation ->
          homeViewModel.setSelectedLocation(geolocation)
          homeViewModel.saveFavoriteLocation()
          homeViewModel.getGeolocation()
          showSearchLocation = !showSearchLocation
        },
        onSubmit = { homeViewModel.fetchGeolocation(search)},
        context = context,
        onNavigateBack = {
            showSearchLocation = false
        }
    )
}
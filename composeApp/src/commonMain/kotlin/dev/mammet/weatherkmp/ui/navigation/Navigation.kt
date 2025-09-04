package dev.mammet.weatherkmp.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import dev.mammet.weatherkmp.ui.components.NavigationType
import dev.mammet.weatherkmp.ui.daily.DailyScreen
import dev.mammet.weatherkmp.ui.home.HomeScreen

private data class NavigationItemContent(
    val icon: ImageVector,
    val text: String,
)

@Composable
fun WeatherNavigation(
    modifier: Modifier = Modifier,
    navigationType: NavigationType,
) {
    val (selectedIndex, setSelectedIndex) = rememberSaveable { mutableIntStateOf(0) }
    val navigationContentList = listOf(
        NavigationItemContent(icon = Icons.Default.Home, "Home"),
        NavigationItemContent(icon = Icons.Default.DateRange, "Forecast")
    )
    WeatherNavContent(
        currentTab = selectedIndex,
        navigationType = navigationType,
        onTabPress = {setSelectedIndex(it)},
        navigationItemContentList = navigationContentList,
        modifier = modifier
    )

}


@Composable
private fun WeatherNavContent(
    modifier: Modifier = Modifier,
    navigationType: NavigationType,
    onTabPress: (Int) -> Unit,
    navigationItemContentList: List<NavigationItemContent>,
    currentTab: Int
) {
    Scaffold(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(it)
                .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            if (navigationType == NavigationType.BOTTOM_NAVIGATION) {
                when (currentTab) {
                    0 -> HomeScreen(navigationType = navigationType,modifier = Modifier.weight(1f))
                    1 -> DailyScreen(modifier = Modifier.weight(1f))
                }
            } else {
                Row {
                    HomeScreen(navigationType = navigationType, modifier = Modifier.weight(1f))
                    Spacer(Modifier.width(32.dp))
                    DailyScreen(modifier = Modifier.weight(1f))
                }
            }
            AnimatedVisibility(
                visible = navigationType == NavigationType.BOTTOM_NAVIGATION
            ){
                WeatherBottomNavigation(
                    currentTab = currentTab,
                    onTabPress = onTabPress,
                    navigationItemContentList = navigationItemContentList,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun WeatherBottomNavigation(
    modifier: Modifier = Modifier,
    currentTab: Int,
    onTabPress: (Int) -> Unit,
    navigationItemContentList: List<NavigationItemContent>
){
    NavigationBar(
       modifier =  modifier
           .height(80.dp),
        containerColor = MaterialTheme.colorScheme.inverseOnSurface,
    ) {
        navigationItemContentList.forEachIndexed { index, navigationItemContent ->
            NavigationBarItem(
                selected = currentTab == index,
                onClick = { onTabPress(index) },
                icon = {
                    Icon(
                        imageVector = navigationItemContent.icon,
                        contentDescription = navigationItemContent.text
                    )
                }
            )
        }
    }
}

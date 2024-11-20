package com.denior.parasol.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.denior.parasol.ui.citySearch.CitySearchScreen
import com.denior.parasol.ui.citySearch.CitySearchViewModel
import com.denior.parasol.ui.home.HomeScreen
import com.denior.parasol.ui.home.HomeViewModel

@Composable
fun ParasolNavGraph(
    navController: NavHostController,
    citiesDrawerAction: () -> Unit, isLoadingCities: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                indexUiState = homeViewModel.indexUiState,
                navigateToCitySearch = { navigateTo(navController, Screen.CitySearch) },
                retryAction = { homeViewModel.retryAction() },
                citiesDrawerAction = citiesDrawerAction, isLoadingCities = isLoadingCities
            )
        }
        composable(route = Screen.CitySearch.route) {
            val searchViewModel: CitySearchViewModel = hiltViewModel()
            CitySearchScreen(
                viewModel = searchViewModel,
                navigateBack = { navController.popBackStack() },
                citySearchByNameUiState = searchViewModel.citySearchByNameUiState,
                reverseSearchByCoordinatesUiState = searchViewModel.cityReverseUiState
            )
        }
    }
}

private fun navigateTo(navController: NavHostController, screen: Screen) {
    try {
        navController.navigate(screen.route) {
            launchSingleTop = true
            restoreState = true
        }
    } catch (e: Exception) {
        Log.e("NavigationError", "Error navigating to ${screen.route}", e)
    }
}

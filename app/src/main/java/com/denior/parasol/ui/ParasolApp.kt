package com.denior.parasol.ui

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.denior.parasol.ui.citySelection.CitiesModalDrawer
import com.denior.parasol.ui.home.HomeViewModel
import com.denior.parasol.ui.navigation.ParasolNavGraph
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ParasolApp(
    navController: NavHostController = rememberNavController(),
    viewModel: HomeViewModel = hiltViewModel()
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val selectedCityId by viewModel.selectedCityId.collectAsState()

    val citiesAvailable = homeUiState.citiesList.isNotEmpty()

    BackHandler(enabled = drawerState.isOpen) {
        scope.launch { drawerState.close() }
    }

    if (citiesAvailable) {
        CitiesModalDrawer(
            cityList = homeUiState.citiesList,
            onCitySelected = { selectedCity ->
                Log.d("CitiesModalDrawer", "City selected: ${selectedCity.cityName}")
                viewModel.setSelectedCity(selectedCity)
                toggleCitiesDrawer(scope, drawerState)
            },
            selectedCityId = selectedCityId,
            drawerState = drawerState,
            drawerStateHeaderAction = { toggleCitiesDrawer(scope, drawerState) },
            content = {
                ParasolNavGraph(
                    navController = navController,
                    citiesDrawerAction = { toggleCitiesDrawer(scope, drawerState) },
                    isLoadingCities = false
                )
            }
        )
    } else {
        ParasolNavGraph(
            navController = navController,
            citiesDrawerAction = {},
            isLoadingCities = true
        )
    }
}

fun toggleCitiesDrawer(scope: CoroutineScope, drawerState: DrawerState) {
    scope.launch {
        if (drawerState.isClosed) {
            drawerState.open()
        } else {
            drawerState.close()
        }
    }
}
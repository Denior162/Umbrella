package com.denior.parasol.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object CitySearch : Screen("citySearch")
}
package com.denior.parasol.ui.citySearch.uiState

import com.denior.parasol.network.model.City

sealed class CitySearchByNameUiState {
    data object Loading : CitySearchByNameUiState()
    data class Success(val result: List<City>) : CitySearchByNameUiState()
    data object Error : CitySearchByNameUiState()
}
package com.denior.parasol.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denior.parasol.data.CitiesRepository
import com.denior.parasol.ui.home.useCases.FetchUVDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UVIndexViewModel @Inject constructor(
    private val fetchUVDataUseCase: FetchUVDataUseCase,
    private val citiesRepository: CitiesRepository
) : ViewModel() {

    private val _indexUiState = MutableStateFlow<IndexUiState>(IndexUiState.Loading)
    val indexUiState: StateFlow<IndexUiState> = _indexUiState

    init {
        observeCitySelection()
    }

    private fun observeCitySelection() {
        viewModelScope.launch {
            citiesRepository.selectedCityId.collect { cityId ->
                cityId?.let { id ->
                    loadCityCoordinatesAndGetUV(id)
                }
            }
        }
    }


    private fun loadCityCoordinatesAndGetUV(cityId: Int) {
        viewModelScope.launch {
            // Fetch the city from the repository instead of injecting the ViewModel
            val city = citiesRepository.getOneCity(cityId).firstOrNull()
            if (city != null) {
                getUVIs(city.latitude, city.longitude)
            } else {
                _indexUiState.value = IndexUiState.Error("City not found")
            }
        }
    }


    private fun getUVIs(latitude: Double, longitude: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            _indexUiState.value = IndexUiState.Loading
            try {
                val response = fetchUVDataUseCase(latitude, longitude)
                _indexUiState.value = IndexUiState.Success(response)
            } catch (e: Exception) {
                _indexUiState.value = IndexUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

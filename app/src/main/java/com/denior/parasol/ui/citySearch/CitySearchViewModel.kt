package com.denior.parasol.ui.citySearch

import android.location.Location
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denior.parasol.data.CitiesRepository
import com.denior.parasol.data.CityEntity
import com.denior.parasol.location.LocationProvider
import com.denior.parasol.network.NominatimReverseApiService
import com.denior.parasol.network.NominatimSearchApiService
import com.denior.parasol.network.model.City
import com.denior.parasol.ui.citySearch.uiState.CitySearchByNameUiState
import com.denior.parasol.ui.citySearch.uiState.ReverseSearchByCoordinatesUiState
import com.denior.parasol.ui.home.HomeUiState
import com.denior.parasol.utils.ErrorHandler.handleError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitySearchViewModel @Inject constructor(
    private val citiesRepository: CitiesRepository,
    private val geocodingSearchByCityName: NominatimSearchApiService,
    private val geocodingReverseSearchByCoordinates: NominatimReverseApiService,
    private val locationProvider: LocationProvider
) : ViewModel() {
    private val _cityCitySearchByNameUiState =
        MutableStateFlow<CitySearchByNameUiState>(CitySearchByNameUiState.Loading)
    val citySearchByNameUiState: StateFlow<CitySearchByNameUiState> = _cityCitySearchByNameUiState

    private val _cityReverseUiState =
        MutableStateFlow<ReverseSearchByCoordinatesUiState>(ReverseSearchByCoordinatesUiState.Loading)
    val cityReverseUiState: StateFlow<ReverseSearchByCoordinatesUiState> = _cityReverseUiState

    private var searchJob: Job? = null
    private val minQueryLength = 3
    private val debounceDelay = 1000L

    val homeUiState: StateFlow<HomeUiState> =
        citiesRepository.getFullListOfCities().map { cities ->
            HomeUiState(cities)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = HomeUiState()
        )

    fun searchCityByName(cityName: String) {
        if (cityName.length < minQueryLength) return

        searchJob?.cancel()

        searchJob = viewModelScope.launch(Dispatchers.IO) {
            _cityCitySearchByNameUiState.value = CitySearchByNameUiState.Loading

            try {
                delay(debounceDelay)
                val result = geocodingSearchByCityName.searchCities(cityName)
                Log.d("SearchCity", "Результат поиска: $result") // Логирование результата
                val addedCities = citiesRepository.getFullListOfCities().first().map { it.cityName }
                val filteredResult = result.filterNot { addedCities.contains(it.name) }

                _cityCitySearchByNameUiState.value = if (filteredResult.isNotEmpty()) {
                    CitySearchByNameUiState.Success(filteredResult)
                } else {
                    CitySearchByNameUiState.Error
                }
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    fun searchCityByCurrentLocation() {
        location?.let { loc ->
            searchCityByCoordinates(loc.latitude, loc.longitude)
        } ?: setError(ErrorType.GenericError(""))
    }

    private fun searchCityByCoordinates(latitude: Double, longitude: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            _cityReverseUiState.value = ReverseSearchByCoordinatesUiState.Loading
            Log.d("ReverseSearch", "Поиск города по координатам: $latitude, $longitude")

            delay(debounceDelay)

            try {
                Log.d("ReverseSearch", "Отправка запроса на поиск города...")
                val result = geocodingReverseSearchByCoordinates.searchCities(latitude, longitude)
                Log.d("ReverseSearch", "Получен результат: $result")
                _cityReverseUiState.value = ReverseSearchByCoordinatesUiState.Success(result)
            } catch (e: Exception) {
                Log.e("ReverseSearch", "Ошибка при поиске по координатам: ${e.message}", e)
                handleError(e)
                _cityReverseUiState.value = ReverseSearchByCoordinatesUiState.Error
            }
        }
    }

    fun addCityToRepository(city: City) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val cityName = city.address.city
                val countryName = city.address.country
                if (cityName.isEmpty()) {
                    city.name
                    Log.e("AddCity", "Адрес или название города отсутствует")
                    return@launch
                }

                val cityEntity = CityEntity(
                    cityName = cityName,
                    countryName = countryName,
                    latitude = city.lat.toDouble(),
                    longitude = city.lon.toDouble()
                )
                citiesRepository.insertCity(cityEntity)
            } catch (e: Exception) {
                Log.e("AddCity", "Ошибка при добавлении города: ${e.message}")
            }
        }
    }

    fun deleteCity(city: CityEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            citiesRepository.deleteCity(city)
        }
    }

    var location by mutableStateOf<Location?>(null)
    private var errorMessage by mutableStateOf<String?>(null)

    sealed class ErrorType {
        data object PermissionDenied : ErrorType()
        data object LocationUnavailable : ErrorType()
        data class GenericError(val message: String) : ErrorType()
    }

    fun setError(error: ErrorType) {
        errorMessage = when (error) {
            is ErrorType.PermissionDenied -> "No permission to access location"
            is ErrorType.LocationUnavailable -> "Failed to get location"
            is ErrorType.GenericError -> error.message
        }
    }

    fun getCurrentLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val loc = locationProvider.getCurrentLocation()
                loc?.let {
                    searchCityByCoordinates(it.latitude, it.longitude)
                } ?: setError(ErrorType.LocationUnavailable)
            } catch (e: SecurityException) {
                setError(ErrorType.PermissionDenied)
            } catch (e: Exception) {
                setError(ErrorType.GenericError(e.message ?: "Unknown error"))
            }
        }
    }
}

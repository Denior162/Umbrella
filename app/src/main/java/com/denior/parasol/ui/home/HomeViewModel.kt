package com.denior.parasol.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denior.parasol.data.CitiesRepository
import com.denior.parasol.data.CityEntity
import com.denior.parasol.data.UserPreferencesRepository
import com.denior.parasol.network.model.UvResponse
import com.denior.parasol.ui.home.useCases.FetchUVDataUseCase
import com.denior.parasol.utils.ErrorHandler.handleError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Represents the UI state for the UV index.
 */
sealed class IndexUiState {
    data object Loading : IndexUiState()
    data class Success(val indexes: UvResponse) : IndexUiState()
    data class Error(val errorMessage: String) : IndexUiState()
}

/**
 * ViewModel for managing home-related data and UI state.
 *
 * @property citiesRepository Repository for city data.
 * @property userPreferencesRepository Repository for user preferences.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchUVDataUseCase: FetchUVDataUseCase,
    private val citiesRepository: CitiesRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _indexUiState = MutableStateFlow<IndexUiState>(IndexUiState.Loading)
    val indexUiState: StateFlow<IndexUiState> = _indexUiState

    private var _selectedCityId = MutableStateFlow<Int?>(null)
    val selectedCityId: StateFlow<Int?> = _selectedCityId

    private val _isFirstLaunch = MutableStateFlow(true)
    val isFirstLaunch: StateFlow<Boolean> = _isFirstLaunch

    /**
     * The current state of the home UI, which includes a list of cities.
     */
    val homeUiState: StateFlow<HomeUiState> =
        citiesRepository.getFullListOfCities().map { cities ->
            if (cities.isEmpty()) {
                _indexUiState.value = IndexUiState.Error(NO_CITIES_AVAILABLE)
                HomeUiState() // Return empty state if no cities are available
            } else {
                HomeUiState(cities) // Return state with available cities
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = HomeUiState()
        )

    val combinedState: StateFlow<Pair<List<CityEntity>, Int?>> =
        combine(
            citiesRepository.getFullListOfCities(),
            _selectedCityId
        ) { cities, selectedId ->
            cities to selectedId
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            emptyList<CityEntity>() to null
        )

    init {
        loadCities()
        observeSelectedCity()
    }

    fun retryAction() {
        _selectedCityId.value?.let { selectedId ->
            viewModelScope.launch(Dispatchers.IO) {
                val cityFlow = citiesRepository.getOneCity(selectedId)
                cityFlow.collect { city ->
                    if (city != null) {
                        getUVIs(latitude = city.latitude, longitude = city.longitude)
                    } else {
                        handleError(Exception("City not found for ID: $selectedId"))
                    }
                }
            }
        } ?: run {
            handleError(Exception("Selected city ID is null."))
        }
    }

    /**
     * Loads cities from the repository and updates the selected city ID if necessary.
     */
    private fun loadCities() {
        viewModelScope.launch(Dispatchers.IO) {
            citiesRepository.getFullListOfCities()
                .distinctUntilChanged()
                .collect { cities ->
                    _isFirstLaunch.value = cities.isEmpty() && _selectedCityId.value == null
                    if (cities.isEmpty()) {
                        _indexUiState.value = IndexUiState.Error(NO_CITIES_AVAILABLE)
                    } else if (_selectedCityId.value == null) {
                        _selectedCityId.value = cities.first().id // Set first city if none selected
                        loadCityCoordinatesAndGetUV()
                    }
                }
        }
    }

    /**
     * Observes changes to the selected city from user preferences.
     */
    private fun observeSelectedCity() {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferencesRepository.selectedCityFlow.collect { cityId ->
                _selectedCityId.value = cityId?.toInt()
                if (_selectedCityId.value != null) {
                    loadCityCoordinatesAndGetUV()
                } else {
                    Log.w("HomeViewModel", "No city selected in DataStore.")
                    _indexUiState.value = IndexUiState.Error(CITY_NOT_SELECTED)
                }
            }
        }
    }

    /**
     * Loads coordinates for the selected city and fetches the UV index.
     */
    private fun loadCityCoordinatesAndGetUV() {
        viewModelScope.launch(Dispatchers.IO) {
            _selectedCityId.value?.let { selectedId ->
                val cityFlow = citiesRepository.getOneCity(selectedId)

                cityFlow.collect { city ->
                    if (city != null) {
                        getUVIs(city.latitude, city.longitude)
                    } else {
                        Log.w("HomeViewModel", "City not found for ID: $selectedId")
                        _indexUiState.value = IndexUiState.Error(CITY_NOT_FOUND)
                    }
                }
            } ?: run {
                Log.w("HomeViewModel", "Selected city ID is null.")
                _indexUiState.value = IndexUiState.Error(CITY_ID_NOT_FOUND)
            }
        }
    }

    /**
     * Sets the currently selected city and saves it to user preferences.
     *
     * @param city The selected CityEntity.
     */
    fun setSelectedCity(city: CityEntity?) {
        city?.let {
            if (it.id != _selectedCityId.value) {
                _selectedCityId.value = it.id
                viewModelScope.launch(Dispatchers.IO) {
                    userPreferencesRepository.saveSelectedCity(it.id.toString())
                    getUVIs(it.latitude, it.longitude)
                }
            }
        }
    }

    /**
     * Fetches UV index values based on latitude and longitude.
     *
     * @param latitude The latitude of the location.
     * @param longitude The longitude of the location.
     */
    private fun getUVIs(latitude: Double, longitude: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            _indexUiState.value = IndexUiState.Loading
            try {
                Log.d("HomeViewModel", "Fetching UV Index for coordinates: ($latitude, $longitude)")
                val response = //uvIndexApi.getIndexes(latitude, longitude)
                    fetchUVDataUseCase(latitude, longitude) // Use the use case
                _indexUiState.value = IndexUiState.Success(response)
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    private fun handleCustomError(error: String) {
        _indexUiState.value = IndexUiState.Error(error)
        Log.w("HomeViewModel", error)
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L

        // Constants for error messages to avoid hardcoded strings in code.
        private const val NO_CITIES_AVAILABLE = "No cities available"
        private const val CITY_NOT_SELECTED = "City not selected"
        private const val CITY_NOT_FOUND = "City not found"
        private const val CITY_ID_NOT_FOUND = "City ID not found"
    }
}

/**
 * Represents the UI state for home, including a list of cities.
 *
 * @property citiesList A list of available CityEntity objects.
 */
data class HomeUiState(
    val citiesList: List<CityEntity> = listOf()
)
package com.denior.parasol.ui.citySelection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denior.parasol.data.CitiesRepository
import com.denior.parasol.data.CityEntity
import com.denior.parasol.data.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitySelectionViewModel @Inject constructor(
    private val citiesRepository: CitiesRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _citiesList = MutableStateFlow<List<CityEntity>>(emptyList())
    val citiesList: StateFlow<List<CityEntity>> = _citiesList

    private val _selectedCityId = MutableStateFlow<Int?>(null)
    val selectedCityId: StateFlow<Int?> = _selectedCityId

    init {
        loadCities()
        observeSelectedCity()
    }

    private fun loadCities() {
        viewModelScope.launch(Dispatchers.IO) {
            citiesRepository.getFullListOfCities()
                .collect { cities ->
                    _citiesList.value = cities
                    if (cities.isNotEmpty() && _selectedCityId.value == null) {
                        _selectedCityId.value = cities.first().id
                    }
                }
        }
    }

    private fun observeSelectedCity() {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferencesRepository.selectedCityFlow.collect { cityId ->
                _selectedCityId.value = cityId?.toInt()
            }
        }
    }

    fun setSelectedCity(city: CityEntity?) {
        city?.let {
            if (it.id != _selectedCityId.value) {
                _selectedCityId.value = it.id
                viewModelScope.launch(Dispatchers.IO) {
                    userPreferencesRepository.saveSelectedCity(it.id.toString())
                }
            }
        }
    }
}

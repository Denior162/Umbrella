package com.denior.parasol.ui.citySearch.useCases

import com.denior.parasol.data.CitiesRepository
import com.denior.parasol.network.NominatimSearchApiService
import com.denior.parasol.network.model.City
import kotlinx.coroutines.flow.first

class CitySearchByNameUseCase(
    private val geocodingSearchByCityName: NominatimSearchApiService,
    private val citiesRepository: CitiesRepository
) {
    suspend fun execute(cityName: String): List<City> {
        if (cityName.length < 3) return emptyList()

        val result = geocodingSearchByCityName.searchCities(cityName)
        val addedCities = citiesRepository.getFullListOfCities().first().map { it.cityName }

        return result.filterNot { addedCities.contains(it.name) }
    }
}

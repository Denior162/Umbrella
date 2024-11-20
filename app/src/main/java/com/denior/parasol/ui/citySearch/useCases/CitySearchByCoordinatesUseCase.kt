package com.denior.parasol.ui.citySearch.useCases

import com.denior.parasol.network.NominatimReverseApiService
import com.denior.parasol.network.model.City

class CitySearchByCoordinatesUseCase(
    private val geocodingReverseSearchByCoordinates: NominatimReverseApiService
) {
    suspend fun execute(latitude: Double, longitude: Double): City {
        return geocodingReverseSearchByCoordinates.searchCities(latitude, longitude)
    }
}

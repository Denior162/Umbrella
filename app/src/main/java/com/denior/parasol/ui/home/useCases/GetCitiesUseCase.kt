package com.denior.parasol.ui.home.useCases

import com.denior.parasol.data.CitiesRepository
import com.denior.parasol.data.CityEntity
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCitiesUseCase @Inject constructor(private val citiesRepository: CitiesRepository) {
    suspend operator fun invoke(): List<CityEntity> {
        return citiesRepository.getFullListOfCities().first()
    }
}
package com.denior.parasol.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfflineCitiesRepository @Inject constructor(
    private val cityDao: CityDao
) : CitiesRepository {
    private val _selectedCityId = MutableStateFlow<Int?>(null)
    override val selectedCityId: StateFlow<Int?> = _selectedCityId.asStateFlow()

    override fun getFullListOfCities(): Flow<List<CityEntity>> = cityDao.getListOFCities()

    override fun getOneCity(id: Int): Flow<CityEntity?> = cityDao.getCity(id)

    override suspend fun insertCity(item: CityEntity) = cityDao.insert(item)

    override suspend fun deleteCity(item: CityEntity) = cityDao.delete(item)

    override suspend fun updateCity(item: CityEntity) = cityDao.update(item)
}

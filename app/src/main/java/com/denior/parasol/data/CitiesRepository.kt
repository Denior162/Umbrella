package com.denior.parasol.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CitiesRepository {

    /**
     * Retrieve all the items from the the given data source.
     */
    fun getFullListOfCities(): Flow<List<CityEntity>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getOneCity(id: Int): Flow<CityEntity?>

    /**
     * Insert item in the data source
     */
    suspend fun insertCity(item: CityEntity)

    /**
     * Delete item from the data source
     */
    suspend fun deleteCity(item: CityEntity)

    /**
     * Update item in the data source
     */
    suspend fun updateCity(item: CityEntity)

    val selectedCityId: StateFlow<Int?>
}
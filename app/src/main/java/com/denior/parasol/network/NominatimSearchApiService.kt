package com.denior.parasol.network

import com.denior.parasol.network.model.City
import retrofit2.http.GET
import retrofit2.http.Query

interface NominatimSearchApiService {
    @GET("search")
    suspend fun searchCities(
        @Query("city") cityName: String,
        @Query("format") format: String = "json"
    ): List<City>
}



package com.denior.parasol.network

import com.denior.parasol.network.model.City
import retrofit2.http.GET
import retrofit2.http.Query

interface NominatimReverseApiService {
    @GET("reverse")
    suspend fun searchCities(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("format") format: String = "json"

    ): City
}
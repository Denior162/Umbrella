package com.denior.parasol.network

import com.denior.parasol.network.model.UvResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrentUvApiService {
    @GET("uvi")
    suspend fun getIndexes(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
    ): UvResponse
}
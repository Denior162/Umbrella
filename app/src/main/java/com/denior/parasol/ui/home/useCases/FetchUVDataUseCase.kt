package com.denior.parasol.ui.home.useCases

import com.denior.parasol.network.CurrentUvApiService
import com.denior.parasol.network.model.UvResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FetchUVDataUseCase @Inject constructor(
    private val uvIndexApi: CurrentUvApiService
) {
    suspend operator fun invoke(
        latitude: Double,
        longitude: Double
    ): UvResponse {
        return uvIndexApi.getIndexes(
            latitude,
            longitude
        )
    }
}
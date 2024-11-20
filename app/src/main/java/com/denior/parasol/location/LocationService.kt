package com.denior.parasol.location

import android.location.Location

interface LocationService {
    suspend fun getCurrentLocation(): Location?
}
package com.denior.parasol.location

import android.location.Location


class LocationProvider(private val locationService: LocationService) {

    suspend fun getCurrentLocation(): Location? {
        return locationService.getCurrentLocation()
    }
}

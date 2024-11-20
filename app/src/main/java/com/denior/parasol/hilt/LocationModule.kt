package com.denior.parasol.hilt

import android.content.Context
import com.denior.parasol.location.FusedLocationService
import com.denior.parasol.location.LocationProvider
import com.denior.parasol.location.LocationService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    @Provides
    @Singleton
    fun provideFusedLocationProvider(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Provides
    @Singleton
    fun provideLocationService(
        @ApplicationContext context: Context,
        fusedLocationClient: FusedLocationProviderClient
    ): LocationService {
        return FusedLocationService(context, fusedLocationClient)
    }

    @Provides
    @Singleton
    fun provideLocationProvider(locationService: LocationService): LocationProvider {
        return LocationProvider(locationService)
    }
}



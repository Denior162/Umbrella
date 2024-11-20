package com.denior.parasol.hilt

import com.denior.parasol.data.CitiesRepository
import com.denior.parasol.data.CityDao
import com.denior.parasol.data.OfflineCitiesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCitiesRepository(cityDao: CityDao): CitiesRepository {
        return OfflineCitiesRepository(cityDao)
    }
}

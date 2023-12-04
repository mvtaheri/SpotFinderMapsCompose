package com.vahid.spotfindermapscompose.di

import android.app.Application
import androidx.room.Room
import com.vahid.spotfindermapscompose.data.ParkingSpotDatabase
import com.vahid.spotfindermapscompose.data.ParkingSpotRepositoryImpl
import com.vahid.spotfindermapscompose.domain.repository.ParkingSpotRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideParkingSpotDatabase(app: Application): ParkingSpotDatabase {
        return Room.databaseBuilder(
            app,
            ParkingSpotDatabase::class.java,
            "parking_spots.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideParkingSpotRepository(db: ParkingSpotDatabase): ParkingSpotRepository {
        return ParkingSpotRepositoryImpl(db.dao)
    }
}
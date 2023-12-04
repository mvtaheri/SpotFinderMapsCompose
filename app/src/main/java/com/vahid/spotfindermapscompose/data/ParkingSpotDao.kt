package com.vahid.spotfindermapscompose.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ParkingSpotDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParkingSpot(spot: ParkingSpotEntity)

    @Delete
    suspend fun deleteParkingSpot(spot: ParkingSpotEntity)

    @Query("SELECT * FROM parkingspotentiry")
    fun getParkingSpot(): Flow<List<ParkingSpotEntity>>
}
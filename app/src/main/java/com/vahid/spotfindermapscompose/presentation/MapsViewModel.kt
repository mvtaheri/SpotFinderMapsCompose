package com.vahid.spotfindermapscompose.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.MapStyleOptions
import com.vahid.spotfindermapscompose.domain.model.ParkingSpot
import com.vahid.spotfindermapscompose.domain.repository.ParkingSpotRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val repository: ParkingSpotRepository
) : ViewModel() {
    var state by mutableStateOf(MapState())

    init {
        viewModelScope.launch {
            repository.getParkingSpots().collectLatest { spots ->
                state = state.copy(
                    parkingSpots = spots
                )
            }
        }
    }

    fun onEvent(event: MapEvent) {
        when (event) {
            is MapEvent.ToggleFalloutMap -> {
                state = state.copy(
                    state.properties.copy(
                        mapStyleOptions = if (state.isFalloutMap) {
                            null
                        } else {
                            MapStyleOptions(MapStyle.json)
                        }
                    )
                )
            }

            is MapEvent.OnMapLongClick -> {
                viewModelScope.launch {
                    repository.insertParkingSpot(
                        ParkingSpot(
                            event.latlng.latitude,
                            event.latlng.longitude
                        )
                    )
                }
            }

            is MapEvent.OnInfoWindowLongClick -> {
                viewModelScope.launch {
                    repository.deleteParkingSpot(
                        event.spot
                    )
                }
            }
        }
    }
}
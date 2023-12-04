package com.vahid.spotfindermapscompose.presentation

import com.google.android.gms.maps.model.LatLng
import com.vahid.spotfindermapscompose.domain.model.ParkingSpot

sealed class MapEvent {
    object ToggleFalloutMap : MapEvent()
    data class OnMapLongClick(val latlng: LatLng) : MapEvent()
    data class OnInfoWindowLongClick(val spot: ParkingSpot) : MapEvent()
}

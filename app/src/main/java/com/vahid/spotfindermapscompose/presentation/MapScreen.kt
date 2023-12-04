package com.vahid.spotfindermapscompose.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ToggleOff
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.UiSettings
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    viewmodel: MapsViewModel = hiltViewModel<MapsViewModel>()
) {
    val uiSettings = remember {
        MapUiSettings(zoomControlsEnabled = false)
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewmodel.onEvent(MapEvent.ToggleFalloutMap)
            }) {
                Icon(
                    imageVector = if (viewmodel.state.isFalloutMap) {
                        Icons.Default.ToggleOff
                    } else Icons.Default.ToggleOn, contentDescription = "Toggle Fallout map"
                )
            }
        }
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = viewmodel.state.properties,
            uiSettings = uiSettings,
            onMapLongClick = {
                viewmodel.onEvent(MapEvent.OnMapLongClick(it))
            }
        ) {
            viewmodel.state.parkingSpots.onEach { spot ->
                Marker(
                    position = LatLng(spot.lat, spot.lng),
                    title = "Parking Spot (${spot.lat}, ${spot.lng})",
                    snippet = "Long Click To Delete",
                    onInfoWindowClick = {
                        viewmodel.onEvent(MapEvent.OnInfoWindowLongClick(spot))
                    },
                    onClick = {
                        it.showInfoWindow()
                        true
                    },
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            }
        }
    }
}
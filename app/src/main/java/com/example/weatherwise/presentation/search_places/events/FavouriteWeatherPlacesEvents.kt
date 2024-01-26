package com.example.weatherwise.presentation.search_places.events

import com.google.android.gms.maps.model.LatLng

sealed class SearchPlacesEvents {
    data class OnPinLocation(val latLng: LatLng): SearchPlacesEvents()
}



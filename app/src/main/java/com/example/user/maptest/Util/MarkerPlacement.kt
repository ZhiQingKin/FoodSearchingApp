package com.example.user.maptest.Util

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MarkerPlacement {
    public fun PlaceMarker(latLng: LatLng, Name: String, mMap: GoogleMap): MarkerOptions {
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        markerOptions.title(Name)
        markerOptions.snippet("user")
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        return markerOptions
    }

    public fun PlaceMarkerBlue(latLng: LatLng, Name: String, mMap: GoogleMap, photoreference: String) {
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        markerOptions.title(Name)
        markerOptions.snippet(photoreference)
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        mMap.addMarker(markerOptions)
    }

}
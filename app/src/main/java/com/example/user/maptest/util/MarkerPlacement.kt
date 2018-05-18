package com.example.user.maptest.util

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MarkerPlacement
{
    /*class that control the market placement on the map within the app*/

    /*function to place green market on the map*/
    public fun PlaceMarker(latLng: LatLng, Name: String, mMap: GoogleMap): MarkerOptions
    {
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        markerOptions.title(Name)
        markerOptions.snippet("user")
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        return markerOptions
    }

    /*function to place blue market on the map*/
    public fun PlaceMarkerBlue(latLng: LatLng, Name: String, mMap: GoogleMap, photoreference: String)
    {
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        markerOptions.title(Name)
        markerOptions.snippet(photoreference)
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        mMap.addMarker(markerOptions)
    }

}
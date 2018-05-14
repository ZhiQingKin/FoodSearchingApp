package com.example.user.maptest.View.Interface

import com.example.user.maptest.Model.Asset.PlaceData
import com.google.android.gms.maps.model.LatLng
import java.util.*

interface MainViewInterface {
    fun displayonmap(latlng: LatLng?)
    fun displaynextview()
    fun CheckGooglePlayServices(): Boolean
    fun displayerror()
    public fun showNearbyPlaces(nearbyPlaceList: List<HashMap<String, String>>): ArrayList<PlaceData>
    fun geoLocate()
    fun NoInternet()
}
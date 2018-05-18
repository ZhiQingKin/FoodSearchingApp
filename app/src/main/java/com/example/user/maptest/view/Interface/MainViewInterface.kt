package com.example.user.maptest.view.Interface

import com.example.user.maptest.model.asset.PlaceData
import com.example.user.maptest.model.gson.placeresult.PlaceDetailResult
import com.example.user.maptest.model.gson.placeresult.ResultPlace
import com.google.android.gms.maps.model.LatLng
import java.util.*

interface MainViewInterface
{
    /*display the the nearby restaurant on the map by using the latlng from the pass-in data into the map*/
    fun displayonmap(latlng: LatLng?)
    /*display the list of the nearby restaurant into list view*/
    fun displaynextview()
    /*check is the google play services condition*/
    fun CheckGooglePlayServices(): Boolean
    /*display error when user try to show an empty list*/
    fun displayerror()
    /*function use to put all the market into the map with the pass-in information*/
    public fun showNearbyPlaces(nearbyPlaceList: PlaceDetailResult): ArrayList<PlaceData>
    /*function that use the data from the search edit text to search for the location of the place*/
    fun geoLocate()
    /*function to check if the mobile have internet connection or not*/
    fun NoInternet()
}
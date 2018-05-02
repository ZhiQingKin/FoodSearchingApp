package com.example.user.maptest.Model.Interface

import android.content.Context
import com.example.user.maptest.Model.Asset.BookmarkLocation
import com.example.user.maptest.Model.Asset.PlaceData
import com.google.android.gms.maps.model.LatLng

interface Model {
    fun addBookmarkLocation(context: Context,placeData: PlaceData)
    fun getBookmarkLocation(context: Context):ArrayList<PlaceData>
    fun deleteBookmarkLocationAll(context: Context)
    fun deleteBookmarkLocation(context: Context,place_id: String)
    fun getBookmarkLocation(context: Context,place_id: String):Boolean
}
package com.example.user.maptest.View.Interface

import com.example.user.maptest.Model.Asset.PlaceData

interface IBookmarkListView {
    fun setadapter(placeDataArray:ArrayList<PlaceData>)
    fun deleteData(place_id:String)
}
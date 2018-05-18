package com.example.user.maptest.view.Interface

import com.example.user.maptest.model.asset.PlaceData

interface IBookmarkListView
{
    /*set the adapter of the bookmark page with the arraylist data retreive from the presenter*/
    fun setadapter(placeDataArray:ArrayList<PlaceData>)
    /*delete the bookmark data from the database */
    fun deleteData(place_id:String)
}
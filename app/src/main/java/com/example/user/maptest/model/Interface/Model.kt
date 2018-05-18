package com.example.user.maptest.model.Interface

import android.content.Context
import com.example.user.maptest.model.asset.PlaceData

interface Model
{
    /*model function that directly interact with the database*/

    /*function to add the bookmark into the database*/
    fun addBookmarkLocation(context: Context,placeData: PlaceData)
    /*function that get the bookmark from the database*/
    fun getBookmarkLocation(context: Context):ArrayList<PlaceData>
    /*function that delete all the bookmark within the database*/
    fun deleteBookmarkLocationAll(context: Context)
    /*function that delete the bookmark from database(one data only)*/
    fun deleteBookmarkLocation(context: Context,place_id: String)
    /*get the bookmark from the location(return true when have data)*/
    fun getBookmarkLocation(context: Context,place_id: String):Boolean
}
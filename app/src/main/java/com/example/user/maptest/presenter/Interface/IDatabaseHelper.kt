package com.example.user.maptest.presenter.Interface

import android.content.Context
import com.example.user.maptest.model.asset.PlaceData

interface IDatabaseHelper
{
    /*function that add the bookmarkdata into the databse*/
    fun addBookmarkData(context: Context, placeData: PlaceData)
    /*function that delete the data from the database*/
    fun deleteBookmarkData(context: Context,place_id: String)
    /*function that check the bookmark is exist or not ? in the database*/
    fun checkBookmarkData(context: Context,place_id: String)
    /*function that get all the data from the database*/
    fun getallBookmarkData(context: Context)
}
package com.example.user.maptest.Presenter.Interface

import android.content.Context
import com.example.user.maptest.Model.Asset.PlaceData

interface IDatabaseHelper {
    fun addBookmarkData(context: Context, placeData: PlaceData)
    fun deleteBookmarkData(context: Context,place_id: String)
    fun checkBookmarkData(context: Context,place_id: String)
    fun getallBookmarkData(context: Context)
}
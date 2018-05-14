package com.example.user.maptest.View.ViewHolder

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.example.user.maptest.R

class BookmarkListHolder {
    lateinit var PlaceNameText:TextView
    lateinit var PlaceAddress:TextView
    lateinit var Ratingtext:TextView
    lateinit var ResRating: RatingBar
    lateinit var RestaurantImage:ImageView


    constructor(rowMain:View)
    {
        PlaceNameText = rowMain.findViewById<TextView>(R.id.textbookmarkPlaceNameList)
        PlaceAddress = rowMain.findViewById<TextView>(R.id.textbookmarkPlaceAddress)
        RestaurantImage = rowMain.findViewById<ImageView>(R.id.bookmarklistImageview)
        ResRating = rowMain.findViewById<RatingBar>(R.id.bookmarkResRating)
        Ratingtext = rowMain.findViewById<TextView>(R.id.bookmarkratingtext)
    }
}
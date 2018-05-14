package com.example.user.maptest.View.ViewHolder

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.example.user.maptest.R

class RestaurantListHolder {
    lateinit var PlaceNameText:TextView
    lateinit var PlaceAddress:TextView
    lateinit var Ratingtext:TextView
    lateinit var ResRating: RatingBar
    lateinit var RestaurantImage: ImageView

    constructor(rowMain:View)
    {
        PlaceNameText = rowMain.findViewById<TextView>(R.id.textPlaceNameList)
        PlaceAddress = rowMain.findViewById<TextView>(R.id.textPlaceAddress)
        RestaurantImage = rowMain.findViewById<ImageView>(R.id.listImageview)
        ResRating = rowMain.findViewById<RatingBar>(R.id.ResRating)
        Ratingtext = rowMain.findViewById<TextView>(R.id.ratingtext)
    }
}
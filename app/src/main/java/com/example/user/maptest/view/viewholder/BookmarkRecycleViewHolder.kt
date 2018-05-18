package com.example.user.maptest.view.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import com.example.user.maptest.R

class BookmarkRecycleViewHolder: RecyclerView.ViewHolder
{
    /*this class contain the data from storing the view data of the recycle view*/
    lateinit var PlaceNameText: TextView
    lateinit var PlaceAddress: TextView
    lateinit var Ratingtext: TextView
    lateinit var ResRating: RatingBar
    lateinit var RestaurantImage: ImageView
    lateinit var BookmarkLayout:LinearLayout

    constructor(rowMain: View?) : super(rowMain)
    {
        PlaceNameText = rowMain!!.findViewById<TextView>(R.id.textbookmarkPlaceNameList)
        PlaceAddress = rowMain!!.findViewById<TextView>(R.id.textbookmarkPlaceAddress)
        RestaurantImage = rowMain!!.findViewById<ImageView>(R.id.bookmarklistImageview)
        ResRating = rowMain!!.findViewById<RatingBar>(R.id.bookmarkResRating)
        Ratingtext = rowMain!!.findViewById<TextView>(R.id.bookmarkratingtext)
        BookmarkLayout = rowMain!!.findViewById<LinearLayout>(R.id.bookmarkviewlayout)
    }
}
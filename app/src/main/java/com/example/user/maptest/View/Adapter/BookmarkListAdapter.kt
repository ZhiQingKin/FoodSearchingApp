package com.example.user.maptest.View.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.example.user.maptest.Model.Asset.PlaceData
import com.example.user.maptest.Model.GETURL.URLGenerator
import com.example.user.maptest.R
import com.squareup.picasso.Picasso

class BookmarkListAdapter : BaseAdapter {
    lateinit var places : ArrayList<PlaceData>
    lateinit var mcontext: Context
    lateinit var urlGenerator: URLGenerator


    constructor(places: ArrayList<PlaceData>, context: Context)
    {
        this.places = places
        this.mcontext=context
        urlGenerator = URLGenerator()
    }

    override fun getView(pos: Int, cview: View?, viewG: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(mcontext)
        val rowMain = layoutInflater.inflate(R.layout.bookmarklayout,viewG,false)
        val PlaceNameText = rowMain.findViewById<TextView>(R.id.textbookmarkPlaceNameList)
        PlaceNameText.text = places[pos].placeName!!
        val PlaceAddress = rowMain.findViewById<TextView>(R.id.textbookmarkPlaceAddress)
        PlaceAddress.text = places[pos].placeAddress
        val RestaurantImage = rowMain.findViewById<ImageView>(R.id.bookmarklistImageview)
        if (places[pos].photoreference != "error") {
            val url: String = urlGenerator.geturl_photoreference(places[pos].photoreference!!)
            Picasso.get().load(url).fit().into(RestaurantImage)
        } else {
            Picasso.get().load(R.drawable.errorloadimage).fit().into(RestaurantImage)
        }
        val ResRating = rowMain.findViewById<RatingBar>(R.id.bookmarkResRating)
        ResRating.rating = places[pos].rating!!.toFloat()
        val Ratingtext = rowMain.findViewById<TextView>(R.id.bookmarkratingtext)
        Ratingtext.text = places[pos].rating
        return rowMain
    }

    override fun getItem(pos: Int): Any {
        return places[pos]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return places.count()
    }
}
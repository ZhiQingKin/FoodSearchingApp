package com.example.user.maptest.View

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.example.user.maptest.Model.PlaceData
import com.example.user.maptest.R
import com.example.user.maptest.R.id.ratingtext
import com.example.user.maptest.R.id.textPlaceNameList
import com.squareup.picasso.Picasso
import org.w3c.dom.Text
import java.util.zip.Inflater

class listviewadapter : BaseAdapter {
    lateinit var places : ArrayList<PlaceData>
    lateinit var mcontext: Context


    constructor(places:ArrayList<PlaceData>,context: Context)
    {
        this.places = places
        this.mcontext=context
    }

    override fun getView(pos: Int, cview: View?, viewG: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(mcontext)
        val rowMain = layoutInflater.inflate(R.layout.customlistview,viewG,false)
        val PlaceNameText = rowMain.findViewById<TextView>(R.id.textPlaceNameList)
        PlaceNameText.text = places[pos].placeName
        val PlaceAddress = rowMain.findViewById<TextView>(R.id.textPlaceAddress)
        PlaceAddress.text = places[pos].placeAddress
        val RestaurantImage = rowMain.findViewById<ImageView>(R.id.listImageview)
        val url:String = places[pos].geturl_photoreference()
        Picasso.get().load(url).fit().into(RestaurantImage)
        val ResRating = rowMain.findViewById<RatingBar>(R.id.ResRating)
        ResRating.rating = places[pos].rating!!.toFloat()
        val Ratingtext = rowMain.findViewById<TextView>(R.id.ratingtext)
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
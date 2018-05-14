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
import com.example.user.maptest.View.ViewHolder.RestaurantListHolder
import com.squareup.picasso.Picasso

class ListViewAdapter : BaseAdapter {
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
        var rowMain = cview
        var restaurantListHolder:RestaurantListHolder ?=null

        if(rowMain==null)
        {
            val layoutInflater = LayoutInflater.from(mcontext)
            rowMain = layoutInflater.inflate(R.layout.customlistview,viewG,false)
            restaurantListHolder = RestaurantListHolder(rowMain)
            rowMain!!.tag = restaurantListHolder
        }
        else
        {
            restaurantListHolder = rowMain!!.tag as RestaurantListHolder
        }


        restaurantListHolder.PlaceNameText.text = places[pos].placeName!!
        restaurantListHolder.PlaceAddress.text = places[pos].placeAddress
        if (places[pos].photoreference != "error") {
            val url: String = urlGenerator.geturl_photoreference(places[pos].photoreference!!)
            Picasso.get().load(url).fit().into(restaurantListHolder.RestaurantImage)
        } else {
            Picasso.get().load(R.drawable.errorloadimage).fit().into(restaurantListHolder.RestaurantImage)
        }
        restaurantListHolder.ResRating.rating = places[pos].rating!!.toFloat()
        restaurantListHolder.Ratingtext.text = places[pos].rating
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
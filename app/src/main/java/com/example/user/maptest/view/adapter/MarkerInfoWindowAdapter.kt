package com.example.user.maptest.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.user.maptest.model.geturl.URLGenerator
import com.example.user.maptest.model.geturl.MarkerCallback
import com.example.user.maptest.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.squareup.picasso.Picasso


class MarkerInfoWindowAdapter : GoogleMap.InfoWindowAdapter
{
    /*adapter that set the data of the market information*/
    var urlGenerator: URLGenerator? = null
    lateinit var context: Context

    constructor(context: Context)
    {
        urlGenerator = URLGenerator()
        this.context = context
    }

    override fun getInfoWindow(marker: Marker): View?
    {
        return null
    }

    @SuppressLint("ResourceAsColor")
    override fun getInfoContents(marker: Marker): View
    {
        val layoutInflater = LayoutInflater.from(context)
        val v = layoutInflater.inflate(R.layout.marker_info, null)
        val markerImage = v.findViewById<ImageView>(R.id.MarkerIcon)
        if (marker.snippet == "error")
        {
            Picasso.with(context).load(R.drawable.errorloadimage).resize(200,100).into(markerImage, MarkerCallback(marker))
            val markerText = v.findViewById<TextView>(R.id.MarketResName)
            markerText.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            markerText.text = marker.title
        }
        else if (marker.snippet == "user")
        {
            Picasso.with(context).load(R.drawable.user).resize(200,100).into(markerImage, MarkerCallback(marker))
            val markerText = v.findViewById<TextView>(R.id.MarketResName)
            markerText.text = marker.title
            markerText.setTextColor(R.color.colorBlack)
        }
        else
        {
            val photourl: String = urlGenerator!!.geturl_photoreference(marker.snippet)
            Picasso.with(context).load(photourl).resize(200,100).into(markerImage, MarkerCallback(marker))
            val markerText = v.findViewById<TextView>(R.id.MarketResName)
            markerText.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            markerText.text = marker.title
        }

        return v
    }
}

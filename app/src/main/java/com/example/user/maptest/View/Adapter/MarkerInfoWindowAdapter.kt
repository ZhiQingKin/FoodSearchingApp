package com.example.user.maptest.View.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.user.maptest.Model.GETURL.URLGenerator
import com.example.user.maptest.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.squareup.picasso.Picasso


class MarkerInfoWindowAdapter : GoogleMap.InfoWindowAdapter {
    var urlGenerator: URLGenerator? = null
    lateinit var context: Context

    constructor(context: Context) {
        urlGenerator = URLGenerator()
        this.context = context
    }

    override fun getInfoWindow(marker: Marker): View? {
        return null
    }

    @SuppressLint("ResourceAsColor")
    override fun getInfoContents(marker: Marker): View {
        val layoutInflater = LayoutInflater.from(context)
        val v = layoutInflater.inflate(R.layout.marker_info, null)
        val markerImage = v.findViewById<ImageView>(R.id.MarkerIcon)
        if (marker.snippet == "error") {
            Picasso.get().load("http://www.gstatic.com/webp/gallery/1.jpg").into(markerImage)
            val markerText = v.findViewById<TextView>(R.id.MarketResName)
            markerText.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            markerText.text = marker.title
        } else if (marker.snippet == "user") {
            Picasso.get().load("http://www.gstatic.com/webp/gallery/1.jpg").into(markerImage)
            val markerText = v.findViewById<TextView>(R.id.MarketResName)
            markerText.text = marker.title
            markerText.setTextColor(R.color.colorBlack)
        } else {
            val photourl: String = urlGenerator!!.geturl_photoreference(marker.snippet)
            Picasso.get().load(photourl).into(markerImage)
            val markerText = v.findViewById<TextView>(R.id.MarketResName)
            markerText.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            markerText.text = marker.title
        }

        return v
    }
}

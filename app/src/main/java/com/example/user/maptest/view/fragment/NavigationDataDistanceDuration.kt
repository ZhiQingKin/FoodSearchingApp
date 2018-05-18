package com.example.user.maptest.view.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.user.maptest.R


class NavigationDataDistanceDuration : Fragment()
{

    var arivalduration: String? = null
    var arivaldistance: String? = null
    var arivaldurationwalk: String? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {

        arivalduration = arguments.getString("duration")
        arivaldistance = arguments.getString("distance")
        arivaldurationwalk = arguments.getString("durationwalk")                //retreive the data from the parent activity

        var view: View? = inflater.inflate(R.layout.fragment_navigation_data_distance_duration, container, false)

        val Durationtv = view!!.findViewById<TextView>(R.id.TextDuration)
        Durationtv.text = "Duration : " + arivalduration

        val Distancetv = view!!.findViewById<TextView>(R.id.TextDistance)                   //set the data of the viewpager from the fragment
        Distancetv.text = "Distance : " + arivaldistance

        val DurationWalktv = view!!.findViewById<TextView>(R.id.Textdurationwalk)
        DurationWalktv.text = "Duration : " + arivaldurationwalk


        return view
    }


}

package com.example.user.maptest.model.geturl

import com.google.android.gms.maps.model.Marker
import com.squareup.picasso.Callback

class MarkerCallback : Callback
{
    /*class that refresh the market info when error occurs or image not loaded*/
    override fun onError()
    {
         //To change body of created functions use File | Settings | File Templates.
    }

    var marker : Marker?= null

    constructor(marker: Marker)
    {
        this.marker=marker;
    }


    override fun onSuccess()
    {
        if (marker == null)
        {
            return;
        }

        if (!marker!!.isInfoWindowShown())
        {
            return;
        }

        // If Info Window is showing, then refresh it's contents:

        marker!!.hideInfoWindow(); // Calling only showInfoWindow() throws an error
        marker!!.showInfoWindow();
    }
}
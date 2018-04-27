package com.example.user.maptest.Model.GETURL

import com.google.android.gms.maps.model.Marker
import com.squareup.picasso.Callback
import java.lang.Exception

class MarkerCallback : Callback {

    var marker : Marker?= null

    constructor(marker: Marker) {
        this.marker=marker;
    }


    override fun onError(e: Exception?) {

    }

    override fun onSuccess() {
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
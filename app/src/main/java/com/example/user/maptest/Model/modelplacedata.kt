package com.example.user.maptest.Model

import com.example.user.maptest.Model.PlaceData

class modelplacedata {
    var placesdata : ArrayList<PlaceData> ?= null

    constructor(placesdata: ArrayList<PlaceData>?) {
        this.placesdata = placesdata
    }

    constructor()
    {
        placesdata = ArrayList<PlaceData>()
        placesdata!!.clear()
    }
}
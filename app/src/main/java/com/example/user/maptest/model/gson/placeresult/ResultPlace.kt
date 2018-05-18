package com.example.user.maptest.model.gson.placeresult

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.google.maps.android.data.Geometry

class ResultPlace (val name:String?,val vicinity:String?,val place_id:String?,val geometry:GeometryLatLng?,val rating:String?,val photos:List<PhotoReference>?)

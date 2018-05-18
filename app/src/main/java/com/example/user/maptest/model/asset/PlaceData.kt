package com.example.user.maptest.model.asset

import java.io.Serializable

class PlaceData : Serializable
{
    /*class that contain all the place data needed in the app*/
    var placeName : String ?=null
    var placeAddress :String ?=null
    var lat : Double ?=null
    var lng : Double ?=null
    var rating : String ?=null
    var place_id : String ?=null
    var photoreference : String ?=null


    constructor(placeName: String?, placeAddress: String?, lat: Double?, lng: Double?, rating: String?,place_id:String?,photoreference:String?)
    {
        this.placeName = placeName
        this.placeAddress = placeAddress
        this.lat = lat
        this.lng = lng
        this.rating = rating
        this.place_id = place_id
        this.photoreference = photoreference
    }

    constructor()


}
package com.example.user.maptest.Model.Asset

import com.google.android.gms.location.places.Place
import com.google.android.gms.maps.model.LatLng
import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class BookmarkLocation(
        @PrimaryKey
        var id:Long = 0,
        @Required
        var LocationName:String ="0",

        @Required
        var placeAddress :String ="",

        @Required
        var rating : String ="",

        @Required
        var place_id : String ="",

        @Required
        var photoreference : String ="",

        @Required
        var lat:String = "0.0",

        @Required
        var lng:String = "0.0"


):RealmObject(){

}

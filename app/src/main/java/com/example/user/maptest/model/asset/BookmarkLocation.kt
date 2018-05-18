package com.example.user.maptest.model.asset

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

/*class that contain and set as the database data in the realm database*/
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

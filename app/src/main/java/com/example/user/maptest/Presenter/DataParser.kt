package com.example.user.maptest.Presenter


import android.util.Log
import java.util.HashMap

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList
import kotlin.math.absoluteValue

/**
 * @author Priyanka
 */

class DataParser {

    private fun getPlace(googlePlaceJson: JSONObject): HashMap<String, String> {
        val googlePlaceMap = HashMap<String, String>()
        var placeName = "--NA--"
        var vicinity = "--NA--"
        var latitude = ""
        var longitude = ""
        var reference = ""
        var rating = ""
        var place_id = ""
        var photoreference=""

        try {
            if (!googlePlaceJson.isNull("name")) {
                placeName = googlePlaceJson.getString("name")
            }
            if (!googlePlaceJson.isNull("vicinity")) {
                vicinity = googlePlaceJson.getString("vicinity")
            }
            if (!googlePlaceJson.isNull("place_id")) {
                place_id = googlePlaceJson.getString("place_id")
            }

            latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat")
            longitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng")

            if (!googlePlaceJson.isNull("rating")) {
                rating = googlePlaceJson.getString("rating")
            }
            else
            {
                rating = "0"
            }
            reference = googlePlaceJson.getString("reference")



            googlePlaceMap["place_name"] = placeName
            googlePlaceMap["vicinity"] = vicinity
            googlePlaceMap["lat"] = latitude
            googlePlaceMap["lng"] = longitude
            googlePlaceMap["reference"] = reference
            googlePlaceMap["rating"] = rating
            googlePlaceMap["photoreference"]= photoreference
            googlePlaceMap["place_id"] = place_id


            if(!googlePlaceJson.getJSONArray("photos").isNull(0))
            {
                photoreference=googlePlaceJson.getJSONArray("photos").getJSONObject(0).getString("photo_reference")
                googlePlaceMap["photoreference"]= photoreference
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return googlePlaceMap

    }

    private fun getPlaces(jsonArray: JSONArray): List<HashMap<String, String>> {
        val count = jsonArray.length()
        val placelist = ArrayList<HashMap<String, String>>()
        var placeMap: HashMap<String, String>? = null

        for (i in 0 until count) {
            try {
                placeMap = getPlace(jsonArray.get(i) as JSONObject)
                placelist.add(placeMap)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }
        return placelist
    }

    fun parse(jsonData: String): List<HashMap<String, String>> {
        var jsonArray: JSONArray? = null
        val jsonObject: JSONObject

       //Log.d("json data", jsonData)

        try {
            jsonObject = JSONObject(jsonData)
            jsonArray = jsonObject.getJSONArray("results")
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return getPlaces(jsonArray!!)
    }


    public fun parseDirections(jsonData: String): Array<String?> {
        var jsonArray: JSONArray? = null
        val jsonObject: JSONObject

        try {
            jsonObject = JSONObject(jsonData)
            jsonArray = jsonObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONArray("steps")
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return getPaths(jsonArray)
    }

    fun getPaths(googleStepsJson: JSONArray?): Array<String?> {
        val count = googleStepsJson!!.length()
        val polylines = arrayOfNulls<String>(count)

        for (i in 0 until count) {
            try {
                polylines[i] = getPath(googleStepsJson.getJSONObject(i))
            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }

        return polylines
    }

    fun getPath(googlePathJson: JSONObject): String {
        var polyline = ""
        try {
            polyline = googlePathJson.getJSONObject("polyline").getString("points")
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return polyline
    }

}
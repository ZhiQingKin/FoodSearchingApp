package com.example.user.maptest.Presenter.Util


import com.example.user.maptest.Model.Asset.Review
import com.example.user.maptest.Presenter.Interface.IDataPerserPresenter
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

/**
 * @author Priyanka
 */

class DataParser : IDataPerserPresenter {

    override fun getPlace(googlePlaceJson: JSONObject): HashMap<String, String> {
        val googlePlaceMap = HashMap<String, String>()
        var placeName = "--NA--"
        var vicinity = "--NA--"
        var latitude = ""
        var longitude = ""
        var reference = ""
        var rating = ""
        var place_id = ""
        var photoreference = "error"

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
            } else {
                rating = "0"
            }
            reference = googlePlaceJson.getString("reference")



            googlePlaceMap["place_name"] = placeName
            googlePlaceMap["vicinity"] = vicinity
            googlePlaceMap["lat"] = latitude
            googlePlaceMap["lng"] = longitude
            googlePlaceMap["reference"] = reference
            googlePlaceMap["rating"] = rating
            googlePlaceMap["photoreference"] = photoreference
            googlePlaceMap["place_id"] = place_id


            if (!googlePlaceJson.getJSONArray("photos").isNull(0)) {
                photoreference = googlePlaceJson.getJSONArray("photos").getJSONObject(0).getString("photo_reference")
                googlePlaceMap["photoreference"] = photoreference
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return googlePlaceMap

    }

    override fun getPlaces(jsonArray: JSONArray): List<HashMap<String, String>> {
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

    override fun parse(jsonData: String): List<HashMap<String, String>> {
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


    public override fun parseDirections(jsonData: String): Array<String?> {
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

    override fun getPaths(googleStepsJson: JSONArray?): Array<String?> {
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

    override fun getPath(googlePathJson: JSONObject): String {
        var polyline = ""
        try {
            polyline = googlePathJson.getJSONObject("polyline").getString("points")
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return polyline
    }


    public override fun parsePlaceDetail(jsonData: String): ArrayList<String?> {
        var jsonArray: JSONArray? = null
        val jsonObject: JSONObject

        try {
            jsonObject = JSONObject(jsonData)
            if (!jsonObject.getJSONObject("result").isNull("photos")) {
                jsonArray = jsonObject.getJSONObject("result").getJSONArray("photos")
            } else {
                val placeDetails = ArrayList<String?>()
                placeDetails.add("error")
                return placeDetails

            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return getDetails(jsonArray)
    }

    override fun getDetails(googleDetailJson: JSONArray?): ArrayList<String?> {
        val count = googleDetailJson!!.length()
        val placeDetails = ArrayList<String?>()


        for (i in 0 until count) {
            try {
                placeDetails.add(getDetail(googleDetailJson.getJSONObject(i)))

            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }

        return placeDetails
    }

    override fun getDetail(googleDetailJson: JSONObject): String {
        var photo_reference = "error"
        try {
            if (!googleDetailJson.isNull("photo_reference")) {
                photo_reference = googleDetailJson.getString("photo_reference")
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return photo_reference
    }


    public override fun parseDuration(jsonData: String): HashMap<String, String> {
        val durationdata = HashMap<String, String>()
        var duration: String = "N/A"
        var distance: String = "N/A"
        val jsonObject: JSONObject

        try {
            jsonObject = JSONObject(jsonData)
            if (!jsonObject.isNull("routes")) {
                if (!jsonObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").isNull(0)) {
                    duration = jsonObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONObject("duration").getString("text")
                }
                distance = jsonObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONObject("distance").getString("text")
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        durationdata["duration"] = duration
        durationdata["distance"] = distance

        return durationdata
    }

    public override fun parseOpeningTime(jsonData: String): ArrayList<String> {
        var jsonArray: JSONArray? = null
        val jsonObject: JSONObject

        try {
            jsonObject = JSONObject(jsonData)
            if (!jsonObject.getJSONObject("result").isNull("opening_hours")) {
                jsonArray = jsonObject.getJSONObject("result").getJSONObject("opening_hours").getJSONArray("weekday_text")
            } else {
                var opening: ArrayList<String> = ArrayList<String>()
                opening.add("N/A")
                return opening
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return getOpeningTimes(jsonArray)
    }

    override fun getOpeningTimes(googleDetailJson: JSONArray?): ArrayList<String> {
        val count = googleDetailJson!!.length()
        val opening = ArrayList<String>()


        for (i in 0 until count) {
            try {
                if (!googleDetailJson.isNull(i)) {
                    opening.add(googleDetailJson.getString(i))
                } else {
                    opening.add("N/A")
                }

            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }

        return opening
    }


    override fun getReviewData(jsonData: String): ArrayList<Review> {
        val reviews: ArrayList<Review> = ArrayList<Review>()
        val jsonObject: JSONObject

        try {
            jsonObject = JSONObject(jsonData)
            if (!jsonObject.getJSONObject("result").isNull("reviews")) {
                val JsonArray = jsonObject.getJSONObject("result").getJSONArray("reviews")
                val count = JsonArray.length() - 1

                for (i in 0..count) {
                    var username: String? = null
                    var userrating: Float? = null
                    var profile_pic_url: String? = null
                    var lastcommentduration: String? = null
                    var commentinfo: String? = null

                    username = JsonArray.getJSONObject(i).getString("author_name")
                    userrating = JsonArray.getJSONObject(i).getString("rating").toFloat()
                    profile_pic_url = JsonArray.getJSONObject(i).getString("profile_photo_url")
                    lastcommentduration = JsonArray.getJSONObject(i).getString("relative_time_description")
                    commentinfo = JsonArray.getJSONObject(i).getString("text")

                    reviews.add(Review(username, userrating, commentinfo, profile_pic_url, lastcommentduration))
                }
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return reviews
    }

}
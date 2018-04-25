package com.example.user.maptest.Presenter.Interface

import com.example.user.maptest.Model.Asset.Review
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

interface IDataPerserPresenter {
    fun getPlace(googlePlaceJson: JSONObject): HashMap<String, String>
    fun getPlaces(jsonArray: JSONArray): List<HashMap<String, String>>
    fun parse(jsonData: String): List<HashMap<String, String>>
    public fun parseDirections(jsonData: String): Array<String?>
    fun getPaths(googleStepsJson: JSONArray?): Array<String?>
    fun getPath(googlePathJson: JSONObject): String
    public fun parsePlaceDetail(jsonData: String): ArrayList<String?>
    fun getDetails(googleDetailJson: JSONArray?): ArrayList<String?>
    fun getDetail(googleDetailJson: JSONObject): String
    public fun parseDuration(jsonData: String): HashMap<String, String>
    public fun parseOpeningTime(jsonData: String): ArrayList<String>
    fun getOpeningTimes(googleDetailJson: JSONArray?): ArrayList<String>
    fun getReviewData(jsonData: String): ArrayList<Review>
}
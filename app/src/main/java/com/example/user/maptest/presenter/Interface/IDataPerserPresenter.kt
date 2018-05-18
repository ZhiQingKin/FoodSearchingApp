package com.example.user.maptest.presenter.Interface

import com.example.user.maptest.model.asset.Review
import com.example.user.maptest.model.gson.navigationresult.NavigationDataResult
import com.example.user.maptest.model.gson.placedetailresult.PlaceDetailFull
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

interface IDataPerserPresenter
{
    /*parsing the direction gson object into arraaylist of points*/
    public fun parseDirections(NavigationDetails:NavigationDataResult): ArrayList<String>
    /*parsing the place details of the gson object into an arraylist of photo reference url*/
    public fun parsePlaceDetail(placeDetail: PlaceDetailFull): ArrayList<String?>
    /*parsing the opening time of the gson object into an arraylist of opening time*/
    public fun parseOpeningTime(placeDetail: PlaceDetailFull): ArrayList<String?>
    /*parsing the review gson object into an arraylist of review class*/
    fun getReviewData(placeDetail: PlaceDetailFull): ArrayList<Review>
}
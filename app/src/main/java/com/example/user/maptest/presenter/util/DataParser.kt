package com.example.user.maptest.presenter.util


import android.util.Log
import com.example.user.maptest.model.asset.Review
import com.example.user.maptest.model.gson.navigationresult.NavigationDataResult
import com.example.user.maptest.model.gson.placedetailresult.PlaceDetailFull
import com.example.user.maptest.presenter.Interface.IDataPerserPresenter
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author Priyanka
 */

class DataParser : IDataPerserPresenter
{
    /*class that use to do data parsing*/

    /*parsing the gson object into the arraylist and filter it*/
    public override fun parseDirections(NavigationDetails:NavigationDataResult): ArrayList<String>
    {
        var PolylinePoints:ArrayList<String> = ArrayList<String>()
        try
        {
               if(NavigationDetails.routes[0].legs[0].steps!= null)
               {
                   for (i in 0..NavigationDetails.routes[0].legs[0].steps.count()-1)
                   {
                       PolylinePoints.add(NavigationDetails.routes[0].legs[0].steps[i].polyline.points)
                   }
               }
        }
        catch (e:IOException)
        {
            Log.d("errror",e.toString())
        }


        return PolylinePoints
    }

    /*parsing the gson object into the arraylist and filter it*/
    public override fun parsePlaceDetail(placeDetail: PlaceDetailFull): ArrayList<String?>
    {
        var photoReferences :ArrayList<String?> = ArrayList<String?>()
        if (placeDetail.result.photos!=null)
        {
            var count = placeDetail!!.result.photos!!.count()-1
            for (i in 0..count)
            {
                photoReferences.add(placeDetail.result.photos!![i].photo_reference!!)
            }
        }
        if(photoReferences.isEmpty())
        {
            photoReferences.add("error")
        }

        return  photoReferences
    }

    /*parsing the gson object into the arraylist and filter it*/
    public override fun parseOpeningTime(placeDetail: PlaceDetailFull): ArrayList<String?>
    {
        var openingtime :ArrayList<String?> = ArrayList<String?>()
        if (placeDetail.result.opening_hours!=null)
        {
            var count = placeDetail!!.result.opening_hours!!.weekday_text!!.count()-1
            for (i in 0..count)
            {
                openingtime.add(placeDetail.result.opening_hours!!.weekday_text!![i])
            }
        }
        if(openingtime.isEmpty())
        {
            openingtime.add("N/A")
        }
        return openingtime
    }

    /*parsing the gson object into the arraylist and filter it*/
    override fun getReviewData(placeDetail: PlaceDetailFull): ArrayList<Review>
    {
        val reviews: ArrayList<Review> = ArrayList<Review>()
        if(placeDetail.result.reviews!=null)
        {
            for (i in 0..placeDetail.result.reviews.count()-1)
            {
                var username: String? = null
                var userrating: Float? = null
                var profile_pic_url: String? = null
                var lastcommentduration: String? = null
                var commentinfo: String? = null

                username = placeDetail.result.reviews[i].author_name
                userrating = placeDetail.result.reviews[i].rating!!.toFloat()
                profile_pic_url = placeDetail.result.reviews[i].profile_photo_url
                lastcommentduration = placeDetail.result.reviews[i].relative_time_description
                commentinfo = placeDetail.result.reviews[i].text

                reviews.add(Review(username, userrating, commentinfo, profile_pic_url, lastcommentduration))
            }
        }

        return reviews
    }

}
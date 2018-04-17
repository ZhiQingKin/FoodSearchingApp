package com.example.user.maptest.Presenter

import android.util.Log
import com.example.user.maptest.View.MapsActivity
import com.example.user.maptest.Model.DownloadURL
import com.example.user.maptest.Model.PlaceData
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers;
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import java.util.HashMap
import io.reactivex.ObservableSource
import java.util.concurrent.Callable
import io.reactivex.disposables.Disposable




/**
 * @author Priyanka
 */

open class GetNearbyPlacesData {
    private var googlePlacesData: String ?=null
    private var mMap: GoogleMap? = null
    lateinit var url: String
    public lateinit var ma: MapsActivity
    public lateinit var placeData: ArrayList<PlaceData>
    constructor(mMap: GoogleMap?, ma: MapsActivity) {
        this.mMap = mMap
        this.ma = ma
        url =""
        placeData = ArrayList<PlaceData>()
    }

    public fun seturl(url:String)
    {
        this.url=url
    }
    public fun setmap(mMap: GoogleMap)
    {
        this.mMap =mMap
    }


    private fun createObservable(): Observable<String> {
        val downloadURL = DownloadURL()
        //Could use fromCallable
        return Observable.defer(object : Callable<ObservableSource<out String>> {
            @Throws(Exception::class)
            override fun call(): Observable<String>?{
                return Observable.just(downloadURL.readUrl(url))
            }
        })
    }

    public fun startthreat()
    {
        createObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<String> {
                    override fun onSubscribe(d: Disposable) {
                       // Log.d(TAG, "onSubscribe: $d")
                    }

                    override fun onNext(value: String) {
                        val nearbyPlaceList: List<HashMap<String, String>>
                        val parser = DataParser()
                        nearbyPlaceList = parser.parse(value)

                        showNearbyPlaces(nearbyPlaceList)

                    }

                    override fun onError(e: Throwable) {
                        Log.d("error",e.toString())
                    }

                    override fun onComplete() {
                        ma.displaynextview()
                    }
                })
    }




    private fun showNearbyPlaces(nearbyPlaceList: List<HashMap<String, String>>) {
        placeData.clear()
        for (i in nearbyPlaceList.indices) {

            val markerOptions = MarkerOptions()
            val googlePlace = nearbyPlaceList[i]

            val placeName = googlePlace["place_name"]
            val vicinity = googlePlace["vicinity"]
            val rating = googlePlace["rating"]
            val photoreference = googlePlace["photoreference"]
            val place_id = googlePlace["place_id"]
            val lat = java.lang.Double.parseDouble(googlePlace["lat"])
            val lng = java.lang.Double.parseDouble(googlePlace["lng"])

            placeData.add(PlaceData(placeName, vicinity, lat, lng, rating,place_id,photoreference))

            val latLng = LatLng(lat, lng)
            markerOptions.position(latLng)
            markerOptions.title("Rating: $rating")
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))

            mMap!!.addMarker(markerOptions)
            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            mMap!!.animateCamera(CameraUpdateFactory.zoomTo(10f))
        }
    }

}
package com.example.user.maptest.presenter.Interface

import com.example.user.maptest.model.asset.PlaceData
import io.reactivex.Observable

interface Presenter
{
    /*create the observable(threat) for generating the json data from web service*/
    public fun createObservable(): Observable<String>
    /*start the threat and parsing the json data into gson object*/
    public fun startthreat()
    /*get the nearby place restaurant by returning the data to MVPView*/
    public fun getnearbyPlaces(): ArrayList<PlaceData>
    /*set the url of the threat*/
    public fun seturl(url: String)
    /*get the duration of the driving travel time with threat*/
    public fun GetDuration(url: String)
    /*get the duration of the walking travel time with threat*/
    public fun GetDurationWalking(url: String)
    /*check the is the array is empty or not*/
    public fun CheckArray()
    /*check the data is within the array or not*/
    public fun CheckWithinList(Name: String): PlaceData?
    /*check the connection of the mobile*/
    public fun CheckConnection()

}
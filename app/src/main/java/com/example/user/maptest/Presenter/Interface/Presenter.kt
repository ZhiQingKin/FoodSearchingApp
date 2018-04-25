package com.example.user.maptest.Presenter.Interface

import com.example.user.maptest.Model.Asset.PlaceData
import io.reactivex.Observable

interface Presenter {
    public fun createObservable(): Observable<String>
    public fun startthreat()
    public fun getnearbyPlaces(): ArrayList<PlaceData>
    public fun seturl(url: String)
    public fun GetDuration(url: String, type: String)
    public fun CheckArray()
    public fun CheckWithinList(Name: String): PlaceData?

}
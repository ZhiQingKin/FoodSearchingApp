package com.example.user.maptest.Presenter


import android.graphics.Color
import com.example.user.maptest.Model.DownloadURL

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import java.util.concurrent.Callable


/**
 * @auth Priyanka
 */

open class GetDirectionsData{

    internal lateinit var  mMap: GoogleMap
    internal lateinit var url: String
    internal lateinit var googleDirectionsData: String

    constructor() {
    }

    public fun setmap(mMap:GoogleMap)
    {
        this.mMap=mMap
    }
    public fun seturl(url:String)
    {
        this.url = url
    }

    private fun createObservable(): Observable<String> {
        val downloadURL = DownloadURL()
        //Could use fromCallable
        return Observable.defer(object : Callable<ObservableSource<out String>> {
            @Throws(Exception::class)
            override fun call(): Observable<String>?{
                //Log.d("data",url)
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
                        //Log.d("data",value)
                        val directionsList: Array<String?>
                        val parser = DataParser()
                        directionsList = parser.parseDirections(value)
                        displayDirection(directionsList)

                    }

                    override fun onError(e: Throwable) {
                        //  Log.e(TAG, "onError: ", e)
                    }

                    override fun onComplete() {
                    }
                })
    }


    fun displayDirection(directionsList: Array<String?>) {

        val count = directionsList.size
        for (i in 0 until count) {
            val options = PolylineOptions()
            options.color(Color.RED)
            options.width(10f)
            options.addAll(PolyUtil.decode(directionsList[i]))
            mMap.addPolyline(options)
        }
    }



}
package com.example.user.maptest.presenter.processdatawithrxjava


import android.content.Context
import com.example.user.maptest.model.asset.PlaceData
import com.example.user.maptest.model.geturl.DownloadURL
import com.example.user.maptest.model.gson.navigationresult.NavigationDataResult
import com.example.user.maptest.model.gson.placeresult.PlaceDetailResult
import com.example.user.maptest.presenter.Interface.IDataPerserPresenter
import com.example.user.maptest.presenter.Interface.Presenter
import com.example.user.maptest.presenter.util.DataParser
import com.example.user.maptest.util.ProgressDialogController
import com.example.user.maptest.view.Interface.ShowDirectionInterface

import com.google.android.gms.maps.GoogleMap
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import java.util.concurrent.Callable

open class GetDirectionsData:Presenter
{
    internal lateinit var  mMap: GoogleMap
    internal lateinit var url: String
    lateinit var MVPView:ShowDirectionInterface
    lateinit var downloadURL: DownloadURL
    var progressDialogController: ProgressDialogController
    var parser: IDataPerserPresenter?=null


    constructor(context: Context,map: GoogleMap,MVPView:ShowDirectionInterface)
    {
        progressDialogController = ProgressDialogController(context)
        this.mMap=map
        this.MVPView = MVPView
        downloadURL = DownloadURL()
        parser = DataParser()
    }

    /*set the url of the class*/
    public override fun seturl(url:String)
    {
        this.url = url
    }

    /*create a threat to get the json data from a url*/
    public override fun createObservable(): Observable<String>
    {
        progressDialogController.progressBarShow()
        //Could use fromCallable
        return Observable.defer(object : Callable<ObservableSource<out String>>
        {
            @Throws(Exception::class)
            override fun call(): Observable<String>?
            {
                return Observable.just(downloadURL.readUrl(url))
            }
        })
    }

    /*start the threat to get the direction data from the google navigation web service and change the view display of the app*/
    public override fun startthreat()
    {
        createObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<String>
                {
                    override fun onSubscribe(d: Disposable)
                    {
                        // Log.d(TAG, "onSubscribe: $d")
                    }

                    override fun onNext(value: String)
                    {
                        val gson = GsonBuilder().create()
                        val NavigationDetails = gson.fromJson(value, NavigationDataResult::class.java)
                        val directionsList: ArrayList<String>
                        directionsList = parser!!.parseDirections(NavigationDetails)
                        MVPView.displayDirection(directionsList)

                    }

                    override fun onError(e: Throwable)
                    {
                        //  Log.e(TAG, "onError: ", e)
                    }

                    override fun onComplete()
                    {
                        progressDialogController.progressBarHide()
                    }
                })
    }


    /*empty*/
    override fun CheckConnection()
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    /*empty*/
    override fun getnearbyPlaces(): ArrayList<PlaceData>
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    /*empty*/
    override fun GetDuration(url: String)
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    /*empty*/
    override fun GetDurationWalking(url: String)
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    /*empty*/
    override fun CheckArray()
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    /*empty*/
    override fun CheckWithinList(Name: String): PlaceData?
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
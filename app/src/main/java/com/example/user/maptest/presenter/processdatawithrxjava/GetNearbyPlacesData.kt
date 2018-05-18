package com.example.user.maptest.presenter.processdatawithrxjava

import android.content.Context
import android.util.Log
import com.example.user.maptest.model.geturl.DownloadURL
import com.example.user.maptest.model.asset.PlaceData
import com.example.user.maptest.model.gson.placeresult.PlaceDetailResult
import com.example.user.maptest.presenter.Interface.IDataPerserPresenter
import com.example.user.maptest.presenter.Interface.Presenter
import com.example.user.maptest.presenter.util.DataParser
import com.example.user.maptest.util.CheckInternetConnection
import com.example.user.maptest.util.MarkerPlacement
import com.example.user.maptest.util.ProgressDialogController
import com.example.user.maptest.view.Interface.MainViewInterface
import com.google.android.gms.location.places.Place
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers;
import com.google.android.gms.maps.GoogleMap
import com.google.gson.GsonBuilder

import java.util.HashMap
import io.reactivex.ObservableSource
import java.util.concurrent.Callable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit


open class GetNearbyPlacesData : Presenter
{
    private var mMap: GoogleMap? = null
    lateinit var url: String
    public lateinit var MVPView:MainViewInterface
    public lateinit var placeData: ArrayList<PlaceData>
    lateinit var markerPlacement:MarkerPlacement
    lateinit var progressDialogController:ProgressDialogController
    lateinit var downloadURL:DownloadURL
    lateinit var context:Context
    lateinit var checkInternetConnection: CheckInternetConnection


    constructor(mMap: GoogleMap?, context: Context,MVPView: MainViewInterface)
    {
        this.mMap = mMap
        this.MVPView = MVPView
        url =""
        placeData = ArrayList<PlaceData>()
        markerPlacement = MarkerPlacement()
        progressDialogController = ProgressDialogController(context)
        downloadURL = DownloadURL()
        this.context = context
        checkInternetConnection = CheckInternetConnection(context)
    }

    /*set the url of the class before starting the threat*/
    public override fun seturl(url:String)
    {
        this.url=url
    }

    /*create the threat for  getting the json data from google place api webservice*/
     override fun createObservable(): Observable<String>
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

    /*create the threat detecting the internet change of the phone will call when start interval 5sec*/
    fun createObservableinternetcheck(): Observable<Long>
    {
        //Could use fromCallable
        return Observable.defer(object : Callable<ObservableSource<out Long>>
        {
            @Throws(Exception::class)
            override fun call(): Observable<Long>?
            {
                return Observable.interval(1000,5000,TimeUnit.MILLISECONDS)
            }
        })
    }

    /*start the threat for parsing and changing the data of the nearby place data and call MVP view to change the view data display*/
    public override fun startthreat()
    {
        createObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry()
                .subscribe(object : Observer<String>
                {
                    override fun onSubscribe(d: Disposable)
                    {
                       // Log.d(TAG, "onSubscribe: $d")
                    }

                    override fun onNext(value: String)
                    {
                        val gson = GsonBuilder().create()
                        val PlaceDetail = gson.fromJson(value, PlaceDetailResult::class.java)
                        placeData = MVPView.showNearbyPlaces(PlaceDetail)

                    }

                    override fun onError(e: Throwable)
                    {
                       // Log.d("error",e.toString())
                    }

                    override fun onComplete()
                    {
                        progressDialogController.progressBarHide()
                    }
                })
    }

    /*start the threat check the connection of the internet*/
    override fun CheckConnection() {
        createObservableinternetcheck()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Long>
                {
                    override fun onComplete()
                    {
                        //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onSubscribe(d: Disposable)
                    {
                        //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onNext(t: Long)
                    {
                        var test = checkInternetConnection.getConnection()
                        if(test)
                        {

                        }
                        else
                        {
                            MVPView.NoInternet()
                        }
                    }

                    override fun onError(e: Throwable)
                    {
                         //To change body of created functions use File | Settings | File Templates.
                    }
                })
    }

    /*return the nearby place data of this class*/
    override fun getnearbyPlaces(): ArrayList<PlaceData>
    {
        return placeData
    }

    /*empty*/
    override fun GetDuration(url: String)
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /*empty*/
    override fun GetDurationWalking(url: String)
    {
        //To change body of created functions use File | Settings | File Templates.
    }

    /*check the data is empty or not*/
    override fun CheckArray()
    {
        if(placeData.count()<=0)
        {
            MVPView.displayerror()
        }
        else
        {
            MVPView.displaynextview()
        }
    }

    /*check the data is within the aarray or not*/
    override fun CheckWithinList(Name: String): PlaceData?
    {
       for (i in placeData.indices)
       {
           if (placeData[i].placeName == Name)
           {
               return placeData[i]
           }
       }
        return null
    }

}
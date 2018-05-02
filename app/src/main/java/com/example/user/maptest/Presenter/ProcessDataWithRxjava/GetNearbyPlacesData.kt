package com.example.user.maptest.Presenter.ProcessDataWithRxjava

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import com.example.user.maptest.View.ActivityView.MapsActivity
import com.example.user.maptest.Model.GETURL.DownloadURL
import com.example.user.maptest.Model.Asset.PlaceData
import com.example.user.maptest.Model.DatabaseModel.LocationDB
import com.example.user.maptest.Model.Interface.Model
import com.example.user.maptest.Presenter.Interface.IDataPerserPresenter
import com.example.user.maptest.Presenter.Interface.Presenter
import com.example.user.maptest.Presenter.Util.DataParser
import com.example.user.maptest.Util.MarkerPlacement
import com.example.user.maptest.Util.ProgressDialogController
import com.example.user.maptest.View.Interface.MainViewInterface
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers;
import com.google.android.gms.maps.GoogleMap

import java.util.HashMap
import io.reactivex.ObservableSource
import java.util.concurrent.Callable
import io.reactivex.disposables.Disposable




/**
 * @author Priyanka
 */

open class GetNearbyPlacesData : Presenter{
    private var mMap: GoogleMap? = null
    lateinit var url: String
    public lateinit var MVPView:MainViewInterface
    public lateinit var placeData: ArrayList<PlaceData>
    lateinit var markerPlacement:MarkerPlacement
    lateinit var progressDialogController:ProgressDialogController
    lateinit var downloadURL:DownloadURL
    var parser:IDataPerserPresenter ?=null


    constructor(mMap: GoogleMap?, context: Context,MVPView: MainViewInterface) {
        this.mMap = mMap
        this.MVPView = MVPView
        url =""
        placeData = ArrayList<PlaceData>()
        markerPlacement = MarkerPlacement()
        progressDialogController = ProgressDialogController(context)
        downloadURL = DownloadURL()
        parser = DataParser()
    }

    public override fun seturl(url:String)
    {
        this.url=url
    }

     override fun createObservable(): Observable<String> {
        progressDialogController.progressBarShow()
        //Could use fromCallable
        return Observable.defer(object : Callable<ObservableSource<out String>> {
            @Throws(Exception::class)
            override fun call(): Observable<String>?{
                return Observable.just(downloadURL.readUrl(url))
            }
        })
    }

    public override fun startthreat()
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
                        nearbyPlaceList = parser!!.parse(value)
                        placeData = MVPView.showNearbyPlaces(nearbyPlaceList)

                    }

                    override fun onError(e: Throwable) {
                       // Log.d("error",e.toString())
                    }

                    override fun onComplete() {
                        progressDialogController.progressBarHide()
                    }
                })
    }


    override fun getnearbyPlaces(): ArrayList<PlaceData> {
        return placeData
    }

    override fun GetDuration(url: String,type:String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override fun CheckArray() {
        if(placeData.count()<=0)
        {
            MVPView.displayerror()
        }
        else
        {
            MVPView.displaynextview()
        }
    }

    override fun CheckWithinList(Name: String): PlaceData? {
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
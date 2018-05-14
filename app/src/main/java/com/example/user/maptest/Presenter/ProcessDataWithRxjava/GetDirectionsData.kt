package com.example.user.maptest.Presenter.ProcessDataWithRxjava


import android.content.Context
import android.graphics.Color
import android.graphics.ColorSpace
import com.example.user.maptest.Model.Asset.PlaceData
import com.example.user.maptest.Model.GETURL.DownloadURL
import com.example.user.maptest.Model.Interface.Model
import com.example.user.maptest.Presenter.Interface.IDataPerserPresenter
import com.example.user.maptest.Presenter.Interface.Presenter
import com.example.user.maptest.Presenter.Util.DataParser
import com.example.user.maptest.Util.ProgressDialogController
import com.example.user.maptest.View.Interface.ShowDirectionInterface

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

open class GetDirectionsData:Presenter{
    internal lateinit var  mMap: GoogleMap
    internal lateinit var url: String
    lateinit var MVPView:ShowDirectionInterface
    lateinit var downloadURL: DownloadURL
    var progressDialogController: ProgressDialogController
    var parser: IDataPerserPresenter?=null


    constructor(context: Context,map: GoogleMap,MVPView:ShowDirectionInterface) {
        progressDialogController = ProgressDialogController(context)
        this.mMap=map
        this.MVPView = MVPView
        downloadURL = DownloadURL()
        parser = DataParser()
    }

    public override fun seturl(url:String)
    {
        this.url = url
    }

    public override fun createObservable(): Observable<String> {
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
                        val directionsList: Array<String?>
                        directionsList = parser!!.parseDirections(value)
                        MVPView.displayDirection(directionsList)

                    }

                    override fun onError(e: Throwable) {
                        //  Log.e(TAG, "onError: ", e)
                    }

                    override fun onComplete() {
                        progressDialogController.progressBarHide()
                    }
                })
    }

    override fun CheckConnection() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }




    override fun getnearbyPlaces(): ArrayList<PlaceData> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun GetDuration(url: String,type:String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override fun CheckArray() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override fun CheckWithinList(Name: String): PlaceData? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
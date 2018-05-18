package com.example.user.maptest.presenter.processdatawithrxjava

import android.content.Context
import android.util.Log
import com.example.user.maptest.model.asset.PlaceData
import com.example.user.maptest.model.databasemodel.LocationDB
import com.example.user.maptest.model.Interface.Model
import com.example.user.maptest.presenter.Interface.IDatabaseHelper
import com.example.user.maptest.view.Interface.DataPageOverviewInterface
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Callable

class GetBookmarkData : IDatabaseHelper
{
    /*process database data through model (mostly call other function (management class))*/
    var model:Model ?=null
    var MVPView:DataPageOverviewInterface?=null

    constructor(context: Context,MVPView: DataPageOverviewInterface?)
    {
        this.model = LocationDB(context)
        this.MVPView = MVPView
    }

    /*create a threat to do the add function for adding the bookmark data into the database*/
    fun createObservableadd(context: Context,placeData: PlaceData): Observable<Unit>
    {
        //Could use fromCallable
        return Observable.defer(object : Callable<ObservableSource<out Unit>>
        {
            @Throws(Exception::class)
            override fun call(): Observable<Unit>?
            {
                return Observable.just(model!!.addBookmarkLocation(context,placeData))
            }
        })
    }

    /*create a threat to do the delete functino for deleting the bookmark data from the database*/
    fun createObservabledelete(context: Context,place_id: String): Observable<Unit>
    {
        //Could use fromCallable
        return Observable.defer(object : Callable<ObservableSource<out Unit>>
        {
            @Throws(Exception::class)
            override fun call(): Observable<Unit>?
            {
                return Observable.just(model!!.deleteBookmarkLocation(context,place_id))
            }
        })
    }

    /*create a threat to get the data from the bookmark database*/
    fun createObservableget(context: Context,place_id: String): Observable<Boolean>
    {
        //Could use fromCallable
        return Observable.defer(object : Callable<ObservableSource<out Boolean>>
        {
            @Throws(Exception::class)
            override fun call(): Observable<Boolean>?
            {
                return Observable.just(model!!.getBookmarkLocation(context,place_id))
            }
        })
    }


    /*start the threat that add the bookmark database*/
    override fun addBookmarkData(context: Context,placeData: PlaceData)
    {
        createObservableadd(context,placeData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    /*start the threat that delete the bookmark database*/
    override fun deleteBookmarkData(context: Context, place_id: String)
    {
        createObservabledelete(context,place_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    /*start the threat to get the data from the bookmark database and check the data if it within the database*/
    override fun checkBookmarkData(context: Context, place_id: String)
    {
        createObservableget(context,place_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Boolean>
                {
                    override fun onComplete()
                    {
                        //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onSubscribe(d: Disposable)
                    {
                        //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onNext(t: Boolean)
                    {

                        MVPView!!.showdata(t)
                    }

                    override fun onError(e: Throwable)
                    {
                        Log.d("errored","fk it")
                    }
                })
    }

    /*empty*/
    override fun getallBookmarkData(context: Context)
    {
        //To change body of created functions use File | Settings | File Templates.
    }

}
package com.example.user.maptest.Presenter.ProcessDataWithRxjava

import android.content.Context
import android.util.Log
import com.example.user.maptest.Model.Asset.PlaceData
import com.example.user.maptest.Model.DatabaseModel.LocationDB
import com.example.user.maptest.Model.Interface.Model
import com.example.user.maptest.Presenter.Interface.IDatabaseHelper
import com.example.user.maptest.Util.ProgressDialogController
import com.example.user.maptest.View.Interface.DataPageOverviewInterface
import com.example.user.maptest.View.Interface.IBookmarkListView
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Callable

class BookmarkListViewData:IDatabaseHelper {

    var model: Model?=null
    var MVPView: IBookmarkListView?=null

    constructor(MVPView: IBookmarkListView?,context: Context) {
        this.MVPView = MVPView
        this.model = LocationDB(context)
    }

    fun createObservabledelete(context: Context,place_id: String): Observable<Unit> {
        //Could use fromCallable
        return Observable.defer(object : Callable<ObservableSource<out Unit>> {
            @Throws(Exception::class)
            override fun call(): Observable<Unit>?{
                return Observable.just(model!!.deleteBookmarkLocation(context,place_id))
            }
        })
    }

    fun createObservablegetall(context: Context): Observable<ArrayList<PlaceData>> {
        //Could use fromCallable
        return Observable.defer(object : Callable<ObservableSource<out ArrayList<PlaceData>>> {
            @Throws(Exception::class)
            override fun call(): Observable<ArrayList<PlaceData>>?{
                return Observable.just(model!!.getBookmarkLocation(context))
            }
        })
    }


    override fun getallBookmarkData(context: Context)
    {
        createObservablegetall(context)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ArrayList<PlaceData>> {
                    override fun onComplete() {
                        //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onSubscribe(d: Disposable) {
                        //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onNext(t: ArrayList<PlaceData>) {
                        MVPView!!.setadapter(t)
                    }

                    override fun onError(e: Throwable) {
                        Log.d("errored","fk it")
                    }
                })
    }

    override fun deleteBookmarkData(context: Context, place_id: String) {
        createObservabledelete(context,place_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }



    override fun checkBookmarkData(context: Context, place_id: String) {
        //To change body of created functions use File | Settings | File Templates.
    }
    override fun addBookmarkData(context: Context, placeData: PlaceData) {
         //To change body of created functions use File | Settings | File Templates.
    }

}
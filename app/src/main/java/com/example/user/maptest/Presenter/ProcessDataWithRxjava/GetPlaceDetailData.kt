package com.example.user.maptest.Presenter.ProcessDataWithRxjava

import android.app.admin.DeviceAdminReceiver
import android.net.wifi.WifiManager
import android.provider.ContactsContract
import android.util.Log
import com.example.user.maptest.Model.Asset.PlaceData
import com.example.user.maptest.Model.GETURL.DownloadURL
import com.example.user.maptest.Model.Asset.Review
import com.example.user.maptest.Model.GETURL.URLGenerator
import com.example.user.maptest.Model.Interface.Model
import com.example.user.maptest.Presenter.Interface.IDataPerserPresenter
import com.example.user.maptest.Presenter.Interface.Presenter
import com.example.user.maptest.Presenter.Util.DataParser
import com.example.user.maptest.View.ActivityView.ShowNavigationDataPage
import com.example.user.maptest.View.Interface.DataPageInterface
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Callable
import kotlin.math.log

class GetPlaceDetailData : Presenter {
    lateinit var PhotoReferenceContainer : ArrayList<String?>
    lateinit var OpeningHourContainer : ArrayList<String>
    lateinit var ReviewContainer: ArrayList<Review>
    lateinit var url :String
    lateinit var MvpView: DataPageInterface
    lateinit var urlGenerator: URLGenerator
    var model: Model?=null
    var durationData = HashMap<String,String>()
    var duration:String ?=null
    var distance:String ?=null
    var parser: IDataPerserPresenter?=null


    constructor(MvpView: DataPageInterface) {
        this.MvpView = MvpView
        urlGenerator = URLGenerator()
        model = DownloadURL()
        parser = DataParser()
    }

    override fun seturl(url: String) {
        this.url=url
    }


    public override fun createObservable(): Observable<String> {
        //Could use fromCallable
        return Observable.defer(object : Callable<ObservableSource<out String>> {
            @Throws(Exception::class)
            override fun call(): Observable<String>?{
                return Observable.just(model!!.readUrl(url))
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
                        PhotoReferenceContainer = parser!!.parsePlaceDetail(value)
                        OpeningHourContainer = parser!!.parseOpeningTime(value)
                        ReviewContainer = parser!!.getReviewData(value)

                    }

                    override fun onError(e: Throwable) {
                        //  Log.e(TAG, "onError: ", e)
                    }

                    override fun onComplete(){
                        MvpView.CreateAdapter(urlGenerator.get_url_all_photoreference(PhotoReferenceContainer))
                        MvpView.displayReview(ReviewContainer)
                        MvpView.displayOpening(OpeningHourContainer)
                        MvpView.displayDuration()
                    }
                })
    }


    public override fun GetDuration(url:String,type:String)
    {
        this.url = url
        createObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<String> {
                    override fun onSubscribe(d: Disposable) {
                        // Log.d(TAG, "onSubscribe: $d")
                    }

                    override fun onNext(value: String) {
                        Log.d("value",value)
                        durationData = parser!!.parseDuration(value)
                        duration = durationData["duration"]
                        distance = durationData["distance"]
                        if(type=="driving")
                        {
                            MvpView.checkdriving(duration,distance)
                        }
                        if(type=="walking")
                        {
                            MvpView.checkwalking(duration)
                        }
                        MvpView.displayDuration()
                    }

                    override fun onError(e: Throwable) {
                        //  Log.e(TAG, "onError: ", e)
                    }

                    override fun onComplete() {

                    }
                })
    }

    override fun getnearbyPlaces(): ArrayList<PlaceData> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override fun CheckArray() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override fun CheckWithinList(Name: String): PlaceData? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
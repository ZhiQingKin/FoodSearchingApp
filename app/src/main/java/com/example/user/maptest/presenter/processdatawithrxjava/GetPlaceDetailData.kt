package com.example.user.maptest.presenter.processdatawithrxjava

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.user.maptest.model.asset.PlaceData
import com.example.user.maptest.model.geturl.DownloadURL
import com.example.user.maptest.model.asset.Review
import com.example.user.maptest.model.geturl.URLGenerator
import com.example.user.maptest.model.gson.durationtravel.DurationTravel
import com.example.user.maptest.model.gson.placedetailresult.PlaceDetailFull
import com.example.user.maptest.model.gson.placeresult.PlaceDetailResult
import com.example.user.maptest.presenter.Interface.IDataPerserPresenter
import com.example.user.maptest.presenter.Interface.Presenter
import com.example.user.maptest.presenter.util.DataParser
import com.example.user.maptest.util.CheckInternetConnection
import com.example.user.maptest.view.Interface.DataPageInterface
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Callable

class GetPlaceDetailData : Presenter
{
    lateinit var PhotoReferenceContainer : ArrayList<String?>
    lateinit var OpeningHourContainer : ArrayList<String?>
    lateinit var ReviewContainer: ArrayList<Review>
    lateinit var url :String
    lateinit var MvpView: DataPageInterface
    lateinit var urlGenerator: URLGenerator
    var downloadURL: DownloadURL?=null
    var duration:String ?=null
    var distance:String ?=null
    var parser: IDataPerserPresenter?=null
    lateinit var checkInternetConnection: CheckInternetConnection
    var context:Context?=null



    constructor(MvpView: DataPageInterface,context: Context)
    {
        this.MvpView = MvpView
        urlGenerator = URLGenerator()
        downloadURL = DownloadURL()
        parser = DataParser()
        this.context = context
        checkInternetConnection = CheckInternetConnection(context)

    }

    /*set the url that the threat use to get json data*/
    override fun seturl(url: String)
    {
        this.url=url
    }

    /*call the threat to get the json data based on the url*/
    public override fun createObservable(): Observable<String>
    {
        //Could use fromCallable
        return Observable.defer(object : Callable<ObservableSource<out String>>
        {
            @Throws(Exception::class)
            override fun call(): Observable<String>?
            {
                return Observable.just(downloadURL!!.readUrl(url))
            }
        })
    }

    /*start the threat to get the place detail data (review,opening time,photo reference array) from the web service and change the view display of the app*/
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
                        val PlaceDetail = gson.fromJson(value, PlaceDetailFull::class.java)
                        PhotoReferenceContainer = parser!!.parsePlaceDetail(PlaceDetail)
                        OpeningHourContainer = parser!!.parseOpeningTime(PlaceDetail)
                        ReviewContainer = parser!!.getReviewData(PlaceDetail)

                    }

                    override fun onError(e: Throwable)
                    {
                        this@GetPlaceDetailData.CheckConnection()
                    }

                    override fun onComplete()
                    {
                        MvpView.CreateAdapter(urlGenerator.get_url_all_photoreference(PhotoReferenceContainer))
                        MvpView.displayReview(ReviewContainer)
                        MvpView.displayOpening(OpeningHourContainer)
                        MvpView.init_Threat_For_Duration_Driving()
                    }
                })
    }

    /*check the connection of the internet of mobile*/
    fun createObservableCheckConnecttion(): Observable<Boolean>
    {
        //Could use fromCallable
        return Observable.defer(object : Callable<ObservableSource<out Boolean>>
        {
            @Throws(Exception::class)
            override fun call(): Observable<Boolean>?{
                return Observable.just(checkInternetConnection.getConnection())
            }
        })
    }

    /*start the threat to get the driving duration travel from the google navigation web service and change the view displat of the app */
    public override fun GetDuration(url:String)
    {
        this.url = url
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
                        val PlaceDetail = gson.fromJson(value, DurationTravel::class.java)
                        if(PlaceDetail.routes[0].legs!=null)
                        {
                            duration = PlaceDetail.routes[0].legs!![0].duration!!.text
                        }
                        else
                        {
                            duration = "N/A"
                        }
                        if(PlaceDetail.routes[0].legs!=null)
                        {
                            distance = PlaceDetail.routes[0].legs!![0].distance!!.text
                        }
                        else
                        {
                            distance = "N/A"
                        }
                    }

                    override fun onError(e: Throwable)
                    {
                          //Log.d( "onError: ", "eror")
                    }

                    override fun onComplete()
                    {
                        MvpView.checkdriving(duration,distance)
                        MvpView.init_Threat_For_Duration_Walking()
                    }
                })
    }

    /*start the threat to get the walking duration travel from the google navigation web service and change the view displat of the app */
    override fun GetDurationWalking(url: String) {
        this.url = url
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
                        val PlaceDetail = gson.fromJson(value, DurationTravel::class.java)
                        if(PlaceDetail.routes[0].legs!=null)
                        {
                            duration = PlaceDetail.routes[0].legs!![0].duration!!.text
                        }
                        else
                        {
                            duration = "N/A"
                        }
                        if(PlaceDetail.routes[0].legs!=null)
                        {
                            distance = PlaceDetail.routes[0].legs!![0].distance!!.text
                        }
                        else
                        {
                            distance = "N/A"
                        }
                    }

                    override fun onError(e: Throwable)
                    {
                        //Log.d( "onError: ", "eror")
                    }

                    override fun onComplete()
                    {
                        MvpView.checkwalking(duration)
                        MvpView.displayDuration()
                    }
                })
    }

    /*function that check the connection of the mobile internet*/
    override fun CheckConnection(){
        var connected:Boolean = false
        createObservableCheckConnecttion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .repeatUntil({
                    connected
                })
                .subscribe(object : Observer<Boolean>{
                    override fun onSubscribe(d: Disposable)
                    {
                        //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onNext(t: Boolean)
                    {
                        if (t)
                        {
                            connected=true
                            (context as Activity).recreate()
                        }
                    }

                    override fun onError(e: Throwable)
                    {
                         //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onComplete()
                    {
                        //To change body of created functions use File | Settings | File Templates.
                    }


                })

    }

    /*empty*/
    override fun getnearbyPlaces(): ArrayList<PlaceData>
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
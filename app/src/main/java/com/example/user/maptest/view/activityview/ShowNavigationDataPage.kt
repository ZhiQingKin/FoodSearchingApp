package com.example.user.maptest.view.activityview

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.user.maptest.model.asset.PlaceData
import com.example.user.maptest.model.asset.Review
import com.example.user.maptest.model.geturl.URLGenerator
import com.example.user.maptest.presenter.Interface.Presenter
import com.example.user.maptest.presenter.processdatawithrxjava.GetPlaceDetailData
import com.example.user.maptest.R
import com.example.user.maptest.util.ProgressDialogController
import com.example.user.maptest.view.adapter.ImageSliderAdapter
import com.example.user.maptest.view.adapter.NavigationPagerAdapter
import com.example.user.maptest.view.fragment.NavigationDataDistanceDuration
import com.example.user.maptest.view.fragment.NavigationDataOpeningTime
import com.example.user.maptest.view.fragment.NavigationDataOverview
import com.example.user.maptest.view.fragment.NavigationDataReview
import com.example.user.maptest.view.Interface.DataPageInterface
import kotlinx.android.synthetic.main.activity_show_navigationdata.*


class ShowNavigationDataPage : AppCompatActivity(), DataPageInterface
{
    lateinit var data: PlaceData
    private var currentlatitude: Double? = null
    private var currentlongtide: Double? = null
    lateinit var navigationDataOpeningTime: NavigationDataOpeningTime
    lateinit var navigationDataDistanceDuration: NavigationDataDistanceDuration
    lateinit var navigationDataReview: NavigationDataReview
    lateinit var presenter: Presenter
    var datadurationwalk: String? = null
    var datadurationdrive: String? = null
    var datadistance: String? = null
    lateinit var urlGenerator: URLGenerator
    lateinit var progressDialogController: ProgressDialogController


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_navigationdata)
        progressDialogController = ProgressDialogController(this)
        progressDialogController.progressBarShow()
        data = intent.getSerializableExtra("place_data") as PlaceData
        urlGenerator = URLGenerator()
        presenter = GetPlaceDetailData(this,this)
        presenter.seturl(urlGenerator.geturl_place_detail(data.place_id!!))
        presenter.startthreat()
        currentlatitude = intent.getDoubleExtra("curlat", 0.1)
        currentlongtide = intent.getDoubleExtra("curlng", 0.1)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle(data.placeName)

        navigateBtn.setOnClickListener({
            DisplayNavigationPage()
        })

        googleNavigateBtn.setOnClickListener({
            openGoogleMapNavigation()
        })
    }

    /*function that create the adapter of the image Slider inside the viewpager*/
    override fun CreateAdapter(ImageContainer: ArrayList<String?>)
    {
        var imageSliderAdapter: ImageSliderAdapter = ImageSliderAdapter(this, ImageContainer)
        SliderRes.adapter = imageSliderAdapter

        ResSliderIndicator.count = imageSliderAdapter.getCount()
        ResSliderIndicator.selection = 0


        SliderRes.addOnPageChangeListener(object : ViewPager.OnPageChangeListener
        {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int)
            {/*empty*/
            }

            override fun onPageSelected(position: Int)
            {
                ResSliderIndicator.setSelection(position)
            }

            override fun onPageScrollStateChanged(state: Int)
            {

            }
        })
    }


    /*start the navigation page function*/
    override fun DisplayNavigationPage()
    {
        var NavigationIntent: Intent = Intent(applicationContext, NavigationMap::class.java)
        NavigationIntent.putExtra("lat", data.lat)
        NavigationIntent.putExtra("lng", data.lng)
        NavigationIntent.putExtra("PlaceName", data.placeName)
        startActivity(NavigationIntent)
    }
    /*start the threat to get driving duration*/
    override fun init_Threat_For_Duration_Driving()
    {
        presenter.GetDuration(urlGenerator.getDirectionsUrlMode(currentlatitude!!, currentlongtide!!, data.lat!!, data.lng!!, "driving"))
    }

    /*start the threat to get the walking duration*/
    override fun init_Threat_For_Duration_Walking()
    {
        presenter.GetDurationWalking(urlGenerator.getDirectionsUrlMode(currentlatitude!!, currentlongtide!!, data.lat!!, data.lng!!, "walking"))
    }

    /*check and set the driving duration and distance */
    override fun checkdriving(duration: String?, distance: String?)
    {
        datadurationdrive = duration
        datadistance = distance
    }

    /*check and set the walking duration*/
    override fun checkwalking(duration: String?)
    {
        datadurationwalk = duration
    }

    /*function that set the display of the fragment of the duration of travel time */
    override fun displayDuration()
    {
            var durationbundle: Bundle = Bundle()
            durationbundle.putString("duration", datadurationdrive)
            durationbundle.putString("distance", datadistance)
            durationbundle.putString("durationwalk", datadurationwalk)
            navigationDataDistanceDuration = NavigationDataDistanceDuration()
            navigationDataDistanceDuration.arguments = durationbundle
            DisplayViewPager()
    }

    /*function that set the display of the fragment of the openning time */
    override fun displayOpening(openingArray: ArrayList<String?>)
    {
        var openingbundle: Bundle = Bundle()
        openingbundle.putStringArrayList("opening", openingArray)
        navigationDataOpeningTime = NavigationDataOpeningTime()
        navigationDataOpeningTime.arguments = openingbundle
    }

    /*function that set the display of the fragment of the review*/
    override fun displayReview(reviewArray: ArrayList<Review>)
    {
        var reviewbundle: Bundle = Bundle()
        reviewbundle.putSerializable("reviews", reviewArray)
        navigationDataReview = NavigationDataReview()
        navigationDataReview.arguments = reviewbundle
    }


    /*function to pass in all the data into the fragment class and set up all the fragment data*/
    private fun DisplayViewPager()
    {
        var adapter: NavigationPagerAdapter = NavigationPagerAdapter(supportFragmentManager)


        var overviewbundle: Bundle = Bundle()
        overviewbundle.putString("place_name", data.placeName)
        overviewbundle.putString("place_address", data.placeAddress)
        overviewbundle.putFloat("place_rating", data.rating!!.toFloat())
        overviewbundle.putSerializable("place_Data",data)
        var navigationDataOverview: NavigationDataOverview = NavigationDataOverview()


        navigationDataOverview.arguments = overviewbundle
        adapter.AddFragment(navigationDataOverview, "Overview")
        adapter.AddFragment(navigationDataOpeningTime, "Opening Time")
        adapter.AddFragment(navigationDataDistanceDuration, "Arrival Duration")
        adapter.AddFragment(navigationDataReview, "Review")


        PlaceDataDisplay.adapter = adapter
        NavigationTBL.setupWithViewPager(PlaceDataDisplay)
        progressDialogController.progressBarHide()
    }

    /*method to call the google map navigation of the phone*/
    private fun openGoogleMapNavigation()
    {
        val uri = "http://maps.google.com/maps?f=d&hl=en&saddr=$currentlatitude,$currentlongtide&daddr=${data.lat},${data.lng}"
        val intent = Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(Intent.createChooser(intent, "Select an application"))
    }

    /*override method to change the function of the back button on the top left of the app*/
    override fun onOptionsItemSelected(item: MenuItem?): Boolean
    {
        var id:Int = item!!.itemId

        if(id==android.R.id.home)
        {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}

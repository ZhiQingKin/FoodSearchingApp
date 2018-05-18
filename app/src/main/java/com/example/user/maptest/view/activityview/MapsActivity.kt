@file:Suppress("DEPRECATION")

package com.example.user.maptest.view.activityview

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import android.widget.Toast
import android.util.Log
import android.view.MenuItem
import com.example.user.maptest.model.asset.PlaceData
import com.example.user.maptest.model.geturl.URLGenerator
import com.example.user.maptest.presenter.Interface.Presenter
import kotlinx.android.synthetic.main.activity_maps.*
import com.example.user.maptest.presenter.processdatawithrxjava.GetNearbyPlacesData
import com.example.user.maptest.R
import com.example.user.maptest.model.gson.placeresult.PlaceDetailResult
import com.example.user.maptest.util.CameraMovement
import com.example.user.maptest.util.MarkerPlacement
import com.example.user.maptest.view.Interface.MainViewInterface
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.common.GoogleApiAvailability;
import java.util.HashMap
import com.google.android.gms.maps.model.LatLng
import com.example.user.maptest.util.AlertDialogController
import com.example.user.maptest.view.adapter.MarkerInfoWindowAdapter
import com.example.user.maptest.view.adapter.PlaceAutocompleteAdapter
import com.example.user.maptest.view.adapter.SearchTextChangeAdapter
import com.example.user.maptest.view.adapter.TextViewAdapter
import com.google.android.gms.location.places.Places
import com.google.android.gms.maps.GoogleMap
import kotlinx.android.synthetic.main.apptoolbar.*
import kotlinx.android.synthetic.main.navigation_drawer_header.view.*
import kotlinx.android.synthetic.main.navigationdrawer_main.*
//import io.realm.Realm
import java.io.IOException


class MapsActivity : AppCompatActivity(), OnMapReadyCallback ,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,LocationListener
,GoogleMap.OnMapClickListener,MainViewInterface,GoogleMap.OnInfoWindowClickListener, NavigationView.OnNavigationItemSelectedListener
{
    private lateinit var mMap: GoogleMap
    private lateinit var client: GoogleApiClient
    private lateinit var locationRequest: LocationRequest
    private var currentlocmarket: Marker? = null
    private var lastlocation: Location? = null
    var firsttry: Boolean = true
    var PROXIMITY_RADIUS: Double = 1000.toDouble()
    var latitude: Double = 0.toDouble()
    var longitude: Double = 0.toDouble()
    lateinit var presenter: Presenter
    lateinit var urlGenerator: URLGenerator
    lateinit var cameraMovement:CameraMovement
    lateinit var markerPlacement: MarkerPlacement
    lateinit var alertDialogController: AlertDialogController
    var latLngBounds: LatLngBounds = LatLngBounds(LatLng(-40.0, -168.0), LatLng(71.0, 136.0))
    var MapClickable:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigationdrawer_main)
        if (!CheckGooglePlayServices()) {
            Log.d("onCreate", "Finishing test case since Google Play Services are not available");
            finish();
        }
        else {
            Log.d("onCreate","Google Play Services available.");
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        urlGenerator = URLGenerator()
        cameraMovement = CameraMovement()
        markerPlacement = MarkerPlacement()
        alertDialogController = AlertDialogController()
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        nav_view.getHeaderView(0).MapSwitch.setOnCheckedChangeListener({
            compoundButton, isChecked ->
            if (isChecked)
            {
                Toast.makeText(this,"On map click function is on", Toast.LENGTH_SHORT).show()
                MapClickable=true
            }
            else
            {
                Toast.makeText(this,"On map click function is off", Toast.LENGTH_SHORT).show()
                MapClickable=false
            }
        })
        showlistviewfab.setOnClickListener({
            presenter.CheckArray()
        })
        clearTextBtn.setOnClickListener({
            autocompletesearch.text.clear()
        })
    }

    /*init the google map*/
    override fun onMapReady(googleMap: GoogleMap)
    {
        mMap = googleMap
        mMap.setOnMapClickListener(this)
        presenter = GetNearbyPlacesData(mMap, this,this)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
        {
            buildGoogleApiClient()
            mMap.setMyLocationEnabled(true)
        }
        presenter.CheckConnection()
        mMap.setOnInfoWindowClickListener(this)
        var markerInfoWinndowAdapter = MarkerInfoWindowAdapter(this)
        mMap.setInfoWindowAdapter(markerInfoWinndowAdapter)
        val  mPlaceAutocompleteAdapter = PlaceAutocompleteAdapter(this, client, latLngBounds, null)
        autocompletesearch.setAdapter(mPlaceAutocompleteAdapter)
        val onEditorActionListener = TextViewAdapter(this)
        autocompletesearch.setOnEditorActionListener(onEditorActionListener)
        val searchTextWatcher = SearchTextChangeAdapter(autocompletesearch,clearTextBtn)
        autocompletesearch.addTextChangedListener(searchTextWatcher)

    }

    /*function use to build google api client to access the google map */
    @Synchronized
    protected fun buildGoogleApiClient()
    {
        client = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .addApi(LocationServices.API)
                .build()

        client!!.connect()
    }
    /*override method that will call over interval time and display the user location market on the map*/
    override fun onLocationChanged(location: Location?)
    {
        lastlocation = location

        val latlng = LatLng(location!!.latitude, location!!.longitude)
        latitude = location!!.latitude
        longitude = location!!.longitude
        if(currentlocmarket!=null)
        {
            currentlocmarket!!.remove()
        }

        currentlocmarket = mMap!!.addMarker(markerPlacement.PlaceMarker(latlng,"FML PERSON",mMap))
        if (firsttry) {
            cameraMovement.CameraMovetoUser(latitude,longitude,mMap)
            firsttry = false
        }


    }

    /*override method that call the on location change per interval time when internet is available*/
    override fun onConnected(p0: Bundle?)
    {
        locationRequest = LocationRequest.create()
        locationRequest.setInterval(50)
        locationRequest.setFastestInterval(1000)
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this)
        }
    }


    override fun onConnectionSuspended(p0: Int)
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnectionFailed(p0: ConnectionResult)
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /*function that start the threat to searcb for the nearby restaurant and call the display function when loaded*/
    private fun display_restaurant()
    {
        mMap.clear()
        val resturant = "restaurant"
        val url: String = urlGenerator.getPlaceUrl(latitude, longitude, resturant,PROXIMITY_RADIUS)
        presenter.seturl(url)
        presenter.startthreat()
    }


    /*check the availbailty of the google play service*/
    override fun CheckGooglePlayServices() : Boolean
    {
        var googleAPI:GoogleApiAvailability = GoogleApiAvailability.getInstance();
        var result:Int = googleAPI.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        0).show();
            }
            return false;
        }
        return true;
    }

    /*display in list view of the market show in map*/
    override fun displaynextview()
    {
        var i : Intent = Intent(applicationContext, Listview::class.java)
        var bundle:Bundle  = Bundle()
        bundle.putSerializable("place_array",presenter.getnearbyPlaces())
        i.putExtras(bundle)
        i.putExtra("curlat",latitude)
        i.putExtra("curlng",longitude)
        startActivity(i)
    }

    /*function that display the bookmark view of the app*/
    fun displaybookmarkview()
    {
        var i : Intent = Intent(applicationContext, BookmarkListView::class.java)
        i.putExtra("curlat",latitude)
        i.putExtra("curlng",longitude)
        startActivity(i)
    }
    /*function that display error when list is empty*/
    override fun displayerror()
    {
        Toast.makeText(this@MapsActivity, "Empty String Please Select Location First", Toast.LENGTH_SHORT).show()
    }
    /*override method that enable the on map click method to call display market function when clicked*/
    override fun onMapClick(pos: LatLng?)
    {
        if(MapClickable)
        {
            displayonmap(pos)
        }
    }
    /*function that use to call the threat to run the nearby restaurant and call the display market function when data is loaded*/
    override fun displayonmap(latlng:LatLng?)
    {
        mMap.clear()
        val resturant = "restaurant"
        val url: String = urlGenerator.getPlaceUrl(latlng!!.latitude, latlng!!.longitude, resturant,PROXIMITY_RADIUS)
        presenter.seturl(url)
        presenter.startthreat()
    }
    /*function that display the market on the map*/
    public override fun showNearbyPlaces(nearbyPlaceList: PlaceDetailResult) :  ArrayList<PlaceData>
    {
        var placesData : ArrayList<PlaceData> = ArrayList<PlaceData>()
        for (i in nearbyPlaceList.results!!.indices) {

            val markerOptions = MarkerOptions()

            val placeName = nearbyPlaceList.results[i].name
            val vicinity = nearbyPlaceList.results[i].vicinity
            var rating:String ?= "0"
            if(nearbyPlaceList.results[i].rating!=null)
            {
                rating=nearbyPlaceList.results[i].rating
            }
            var photoreference:String ?="error"
            if(nearbyPlaceList.results[i].photos!=null)
            {
                photoreference = nearbyPlaceList.results[i].photos!![0].photo_reference
            }
            val place_id = nearbyPlaceList.results[i].place_id
            val lat = java.lang.Double.parseDouble(nearbyPlaceList.results[i].geometry!!.location!!.lat)
            val lng = java.lang.Double.parseDouble(nearbyPlaceList.results[i].geometry!!.location!!.lng)

            placesData.add(PlaceData(placeName, vicinity, lat, lng, rating, place_id, photoreference))

            val latLng = LatLng(lat, lng)
            markerPlacement.PlaceMarkerBlue(latLng,placeName!!,mMap,photoreference!!)

        }

        return placesData
    }

    /*override method to use to move the user to specify data page with the market click*/
    override fun onInfoWindowClick(marker : Marker?)
    {
       val retriveData = presenter.CheckWithinList(marker!!.title)
        if(retriveData!=null)
        {
            var i : Intent = Intent(applicationContext, ShowNavigationDataPage::class.java)
            i.putExtra("place_data",retriveData)
            i.putExtra("curlat",latitude)
            i.putExtra("curlng",longitude)
            startActivity(i)
        }
        else
        {
            Toast.makeText(this,"This is User",Toast.LENGTH_LONG).show()
        }
    }

    override fun geoLocate()
    {
        var edit_text_data = autocompletesearch.text.toString()
        var geocoder: Geocoder = Geocoder(this)
        var list_of_address:List<Address>  = ArrayList()
        try{
            list_of_address = geocoder.getFromLocationName(edit_text_data, 2)
        }
        catch (e: IOException){
            e.printStackTrace()
        }

        if (list_of_address!!.count() > 0) {
            val address = list_of_address.get(0)
            displayonmap(LatLng(address.latitude,address.longitude))
            cameraMovement.CameraMovetoUser(address.latitude,address.longitude,mMap)
        }

    }

    /*override method that use to change the back icon of the phone and to comfirm the user exit decision*/
    override fun onBackPressed()
    {
        alertDialogController.CreateExitDialog(this)
    }
    /*display no internet when detected*/
    override fun NoInternet()
    {
        Toast.makeText(this,"Unable to connect to the internet, \n Please check your internet connection ",Toast.LENGTH_SHORT).show()
    }

    /*override method to set the on click listener of the navigation drawer inside the app*/
    override fun onNavigationItemSelected(item: MenuItem): Boolean
    {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_checklocation -> {
                display_restaurant()
            }
            R.id.nav_showlist -> {
                presenter.CheckArray()
            }
            R.id.nav_bookmark -> {
                displaybookmarkview()
            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

}

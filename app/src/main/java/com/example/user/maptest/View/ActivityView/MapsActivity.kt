@file:Suppress("DEPRECATION")

package com.example.user.maptest.View.ActivityView

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.view.WindowManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.media.Image
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.text.Editable
import android.text.TextWatcher
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
import android.view.View
import android.widget.ImageView
import com.example.user.maptest.Model.Asset.PlaceData
import com.example.user.maptest.Model.GETURL.URLGenerator
import com.example.user.maptest.Presenter.Interface.Presenter
import kotlinx.android.synthetic.main.activity_maps.*
import com.example.user.maptest.Presenter.ProcessDataWithRxjava.GetNearbyPlacesData
import com.example.user.maptest.R
import com.example.user.maptest.Util.CameraMovement
import com.example.user.maptest.Util.MarkerPlacement
import com.example.user.maptest.View.Interface.MainViewInterface
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.common.GoogleApiAvailability;
import java.util.HashMap
import com.google.android.gms.maps.model.LatLng
import android.widget.TextView
import com.example.user.maptest.Model.DatabaseModel.LocationDB
import com.example.user.maptest.R.id.toolbar
import com.example.user.maptest.Util.AlertDialogController
import com.example.user.maptest.View.Adapter.MarkerInfoWindowAdapter
import com.example.user.maptest.View.Adapter.PlaceAutocompleteAdapter
import com.example.user.maptest.View.Adapter.SearchTextChangeAdapter
import com.example.user.maptest.View.Adapter.TextViewAdapter
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.Places
import com.google.android.gms.maps.GoogleMap
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.apptoolbar.*
import kotlinx.android.synthetic.main.navigation_drawer_header.*
import kotlinx.android.synthetic.main.navigation_drawer_header.view.*
import kotlinx.android.synthetic.main.navigationdrawer_main.*
//import io.realm.Realm
import java.io.IOException


class MapsActivity : AppCompatActivity(), OnMapReadyCallback ,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,LocationListener
,GoogleMap.OnMapClickListener,MainViewInterface,GoogleMap.OnInfoWindowClickListener, NavigationView.OnNavigationItemSelectedListener{
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

    override fun onCreate(savedInstanceState: Bundle?) {
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

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapClickListener(this)
        presenter = GetNearbyPlacesData(mMap, this,this)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
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


    @Synchronized
    protected fun buildGoogleApiClient() {
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

    override fun onLocationChanged(location: Location?) {
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


    override fun onConnected(p0: Bundle?) {
        locationRequest = LocationRequest.create()
        locationRequest.setInterval(50)
        locationRequest.setFastestInterval(1000)
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this)
        }
    }


    override fun onConnectionSuspended(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private fun display_restaurant() {
        mMap.clear()
        val resturant = "restaurant"
        val url: String = urlGenerator.getPlaceUrl(latitude, longitude, resturant,PROXIMITY_RADIUS)
        presenter.seturl(url)
        presenter.startthreat()
    }



    override fun CheckGooglePlayServices() : Boolean {
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

    fun displaybookmarkview()
    {
        var i : Intent = Intent(applicationContext, BookmarkListView::class.java)
        i.putExtra("curlat",latitude)
        i.putExtra("curlng",longitude)
        startActivity(i)
    }

    override fun displayerror()
    {
        Toast.makeText(this@MapsActivity, "Empty String Please Select Location First", Toast.LENGTH_SHORT).show()
    }

    override fun onMapClick(pos: LatLng?) {
        if(MapClickable)
        {
            displayonmap(pos)
        }
    }

    override fun displayonmap(latlng:LatLng?)
    {
        mMap.clear()
        val resturant = "restaurant"
        val url: String = urlGenerator.getPlaceUrl(latlng!!.latitude, latlng!!.longitude, resturant,PROXIMITY_RADIUS)
        presenter.seturl(url)
        presenter.startthreat()
    }

    public override fun showNearbyPlaces(nearbyPlaceList: List<HashMap<String, String>>) :  ArrayList<PlaceData>
    {
        var placesData : ArrayList<PlaceData> = ArrayList<PlaceData>()
        for (i in nearbyPlaceList.indices) {

            val markerOptions = MarkerOptions()
            val googlePlace = nearbyPlaceList[i]

            val placeName = googlePlace["place_name"]
            val vicinity = googlePlace["vicinity"]
            val rating = googlePlace["rating"]
            val photoreference = googlePlace["photoreference"]
            val place_id = googlePlace["place_id"]
            val lat = java.lang.Double.parseDouble(googlePlace["lat"])
            val lng = java.lang.Double.parseDouble(googlePlace["lng"])

            placesData.add(PlaceData(placeName, vicinity, lat, lng, rating, place_id, photoreference))

            val latLng = LatLng(lat, lng)
            markerPlacement.PlaceMarkerBlue(latLng,placeName!!,mMap,photoreference!!)

        }

        return placesData
    }

    override fun onInfoWindowClick(marker : Marker?) {
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

    override fun geoLocate() {
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

    override fun onBackPressed() {
        alertDialogController.CreateExitDialog(this)
    }

    override fun NoInternet() {
        Toast.makeText(this,"Unable to connect to the internet, \n Please check your internet connection ",Toast.LENGTH_SHORT).show()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
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

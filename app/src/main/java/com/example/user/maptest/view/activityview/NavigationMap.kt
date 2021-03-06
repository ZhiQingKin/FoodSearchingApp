package com.example.user.maptest.view.activityview

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.example.user.maptest.model.geturl.URLGenerator
import com.example.user.maptest.presenter.Interface.Presenter
import com.example.user.maptest.presenter.processdatawithrxjava.GetDirectionsData
import com.example.user.maptest.R
import com.example.user.maptest.util.CameraMovement
import com.example.user.maptest.util.MarkerPlacement
import com.example.user.maptest.view.Interface.ShowDirectionInterface
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.PolyUtil
import kotlinx.android.synthetic.main.activity_navigation_map.*

class NavigationMap : AppCompatActivity(), OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener,ShowDirectionInterface
{


    private lateinit var mMap: GoogleMap
    private lateinit var client: GoogleApiClient
    private lateinit var locationRequest: LocationRequest
    private var currentlocmarket: Marker? = null
    lateinit var presenter: Presenter
    private var lastlocation: Location? = null
    var firsttry: Boolean = true
    private var locationlat:Double ?=null
    private var locationlng:Double ?=null
    private var placeName:String ?=null
    private var urlGenerator:URLGenerator ?=null
    lateinit var cameraMovement: CameraMovement
    lateinit var markerPlacement: MarkerPlacement

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        locationlat = intent.getDoubleExtra("lat",0.1)
        locationlng = intent.getDoubleExtra("lng",0.1)
        placeName = intent.getStringExtra("PlaceName")
        urlGenerator = URLGenerator()
        cameraMovement = CameraMovement()
        markerPlacement = MarkerPlacement()

        Cancelbtn.setOnClickListener({
            finish()
        })

    }

    /*inti the google map and the data needed in the map*/
    override fun onMapReady(googleMap: GoogleMap)
    {
        mMap = googleMap
        presenter = GetDirectionsData(this,mMap,this)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
        {
            buildGoogleApiClient()
            mMap.setMyLocationEnabled(true)
        }
        val markerOptions = MarkerOptions()
        markerOptions.position(LatLng(locationlat!!,locationlng!!))
        markerOptions.title(placeName)
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        mMap!!.addMarker(markerOptions)


    }

    /*function that create the google api client*/
    @Synchronized
   fun buildGoogleApiClient()
    {
        client = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()

        client!!.connect()
    }

    /*will call when internet is connected*/
    override fun onConnected(p0: Bundle?)
    {
        locationRequest = LocationRequest.create()
        locationRequest.setInterval(50)
        locationRequest.setFastestInterval(1000)
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
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

    /*detect location change and continue to refresh the navigation data in case of movement change*/
    override fun onLocationChanged(location: Location?)
    {
        lastlocation = location

        val latlng = LatLng(location!!.latitude, location!!.longitude)
        if(currentlocmarket!=null)
        {
            currentlocmarket!!.remove()
        }

        currentlocmarket = mMap!!.addMarker(markerPlacement.PlaceMarker(latlng,"FML PERSON",mMap))
        if (firsttry) {
            cameraMovement.CameraMovetoUser(location!!.latitude, location!!.longitude,mMap)
            firsttry = false
        }
        StartDirectionSearch()
    }

    /*function use to start the threat for getting the direction from the web services*/
    override fun StartDirectionSearch()
    {

        var url: String = urlGenerator!!.getDirectionsUrl(lastlocation!!.latitude,lastlocation!!.longitude,locationlat!!,locationlng!!)
        presenter.seturl(url)
        presenter.startthreat()
    }

    /*function that use to display the polyline inside the map*/
    public override fun displayDirection(directionsList: ArrayList<String>)
    {

        val count = directionsList.size
        for (i in 0 until count) {
            val options = PolylineOptions()
            options.color(Color.RED)
            options.width(10f)
            options.addAll(PolyUtil.decode(directionsList[i]))
            mMap.addPolyline(options)
        }
    }



}

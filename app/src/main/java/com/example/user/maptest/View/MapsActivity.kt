@file:Suppress("DEPRECATION")

package com.example.user.maptest.View

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import android.widget.Toast
import android.support.v4.app.ActivityCompat
import android.util.Log
import kotlinx.android.synthetic.main.activity_maps.*
import com.example.user.maptest.Presenter.GetDirectionsData
import com.example.user.maptest.Presenter.GetNearbyPlacesData
import com.example.user.maptest.R
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.common.GoogleApiAvailability;


class MapsActivity : AppCompatActivity(), OnMapReadyCallback ,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,LocationListener
,GoogleMap.OnMarkerClickListener,GoogleMap.OnMapClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var client: GoogleApiClient
    private lateinit var locationRequest: LocationRequest
    private var lastlocation: Location? = null
    private var currentlocmarket: Marker? = null
    var firsttry: Boolean = true
    var PROXIMITY_RADIUS: Double = 1000.toDouble()
    var latitude: Double = 0.toDouble()
    var longitude: Double = 0.toDouble()
    var end_latitude: Double = 0.toDouble()
    var end_longitude:Double = 0.toDouble()
    lateinit var getNearbyPlacesData: GetNearbyPlacesData
    lateinit var getDirectionsData: GetDirectionsData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
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

        checkres.setOnClickListener({
            display_restaurant()
        })

    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMarkerClickListener(this)
        mMap.setOnMapClickListener(this)
        getNearbyPlacesData = GetNearbyPlacesData(mMap, this)
        getDirectionsData = GetDirectionsData()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient()
            mMap.setMyLocationEnabled(true)
        }
    }


    @Synchronized
    protected fun buildGoogleApiClient() {
        client = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
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
        val markerOptions = MarkerOptions()
        markerOptions.position(latlng)
        markerOptions.title("FML PERSON")
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))

        currentlocmarket = mMap!!.addMarker(markerOptions)
        if (firsttry) {
            movetouser()
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


    private fun getUrl(latitude: Double, longitude: Double, nearbyPlace: String): String {

        val googlePlaceUrl = StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?")
        googlePlaceUrl.append("location=$latitude,$longitude")
        googlePlaceUrl.append("&radius=$PROXIMITY_RADIUS")
        googlePlaceUrl.append("&type=$nearbyPlace")
        googlePlaceUrl.append("&sensor=true")
        googlePlaceUrl.append("&key=" + "AIzaSyC3L5cQpZH1iEMbKQZICebrDDfGaxDiSNI")
        Log.d("MapsActivity", "url = " + googlePlaceUrl.toString())

        return googlePlaceUrl.toString()
    }


    fun display_restaurant() {
        mMap.clear()
        val resturant = "restaurant"
        val url: String = getUrl(latitude, longitude, resturant)
        getNearbyPlacesData.seturl(url)
        getNearbyPlacesData.setmap(mMap)
        getNearbyPlacesData.startthreat()
        Toast.makeText(this@MapsActivity, "Showing Nearby Restaurants", Toast.LENGTH_SHORT).show()
    }


    fun movetouser() {
        val cameraPosition = CameraPosition.Builder()
                .target(LatLng(latitude, longitude))
                .zoom(16f)                   // Sets the zoom
                .bearing(90f)                // Sets the orientation of the camera to east
                .tilt(30f)                   // Sets the tilt of the camera to 30 degrees
                .build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }



    private fun getDirectionsUrl(): String {
        val googleDirectionsUrl = StringBuilder("https://maps.googleapis.com/maps/api/directions/json?")
        googleDirectionsUrl.append("origin=$latitude,$longitude")
        googleDirectionsUrl.append("&destination=$end_latitude,$end_longitude")
        googleDirectionsUrl.append("&key=" + "AIzaSyA1916xA7UxvWV-aQbkgZ_vMrnwAxzW8dA")

        return googleDirectionsUrl.toString()
    }

    private fun CheckGooglePlayServices() : Boolean {
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

    override fun onMarkerClick(marker: Marker?): Boolean {
        Toast.makeText(this@MapsActivity,"let start",Toast.LENGTH_LONG).show()
        end_latitude = marker!!.position.latitude
        end_longitude = marker!!.position.longitude
        var url: String = getDirectionsUrl()
        getDirectionsData.setmap(mMap)
        getDirectionsData.seturl(url)
        getDirectionsData.startthreat()
        return true
    }

    fun displaynextview()
    {
        var i : Intent = Intent(applicationContext,Listview::class.java)
        var bundle:Bundle  = Bundle()
        bundle.putSerializable("place_array",getNearbyPlacesData.placeData)
        i.putExtras(bundle)
        startActivity(i)
    }
    override fun onMapClick(pos: LatLng?) {

        displayonmap(pos)
    }


    fun displayonmap(latlng:LatLng?)
    {
        mMap.clear()
        val resturant = "restaurant"
        val url: String = getUrl(latlng!!.latitude, latlng.longitude, resturant)
        getNearbyPlacesData.seturl(url)
        getNearbyPlacesData.setmap(mMap)
        getNearbyPlacesData.startthreat()
    }
}

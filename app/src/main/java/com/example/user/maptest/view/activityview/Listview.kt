package com.example.user.maptest.view.activityview

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.example.user.maptest.model.asset.PlaceData
import com.example.user.maptest.R
import com.example.user.maptest.view.adapter.ShowRestaurantListAdapter
import kotlinx.android.synthetic.main.activity_listview.*
import java.util.*
import kotlin.Comparator

class Listview : AppCompatActivity()
{
    lateinit var place_array : ArrayList<PlaceData>
    private var currentlatitude:Double ?=null
    private var currentlongtide:Double ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listview)
        val bundleobj : Bundle = intent.extras
        currentlatitude = intent.getDoubleExtra("curlat",0.1)
        currentlongtide = intent.getDoubleExtra("curlng",0.1)
        place_array = bundleobj.getSerializable("place_array") as ArrayList<PlaceData>          //get the data from pervious activity
        sortplace_array()
        place_list_view.setHasFixedSize(true)
        place_list_view.setLayoutManager(LinearLayoutManager(this))
        place_list_view.adapter = ShowRestaurantListAdapter(place_array, this,this)     //set the adapter of the list view
        //place_list_view.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
          //  displaynextview(position) })
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle("Restaurant Available")
    }

    /*display the show specify data activity base on the data from the user pick*/
    fun displaynextview(position:Int)
    {
        var i : Intent = Intent(applicationContext, ShowNavigationDataPage::class.java)
        i.putExtra("place_data",place_array[position])
        i.putExtra("curlat",currentlatitude)
        i.putExtra("curlng",currentlongtide)
        startActivity(i)
    }

    /*sort the array into alpabetical order*/
    fun sortplace_array()
    {
        Collections.sort(place_array,object : Comparator<PlaceData>
        {
            override fun compare(p0: PlaceData?, p1: PlaceData?): Int
            {
                val place1 = p0!!.placeName!!.toUpperCase()
                val place2 = p1!!.placeName!!.toUpperCase()

                return place1.compareTo(place2)
            }
        })
    }

    /*override method use to change the function of the back button on the top left of the app*/
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

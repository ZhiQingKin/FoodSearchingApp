package com.example.user.maptest.view.activityview

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.widget.AdapterView
import com.example.user.maptest.model.asset.PlaceData
import com.example.user.maptest.presenter.Interface.IDatabaseHelper
import com.example.user.maptest.presenter.processdatawithrxjava.BookmarkListViewData
import com.example.user.maptest.R
import com.example.user.maptest.util.AlertDialogController
import com.example.user.maptest.view.adapter.BookmarkListAdapter
import com.example.user.maptest.view.Interface.IBookmarkListView
import kotlinx.android.synthetic.main.activity_bookmark_list_view.*
import java.util.*
import kotlin.collections.ArrayList

class BookmarkListView : AppCompatActivity(),IBookmarkListView
{
    lateinit var place_array : ArrayList<PlaceData>
    private var currentlatitude:Double ?=null
    private var currentlongtide:Double ?=null
    private var presenter: IDatabaseHelper ?=null
    private var refreshafterresume:Boolean =false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark_list_view)
        val bundleobj : Bundle = intent.extras
        presenter = BookmarkListViewData(this,this)
        currentlatitude = intent.getDoubleExtra("curlat",0.1)
        currentlongtide = intent.getDoubleExtra("curlng",0.1)
        supportActionBar!!.title = "Bookmark"
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        getData()
    }

    /*set the adapter of the list view*/
    override fun setadapter(placeDataArray:ArrayList<PlaceData>)
    {
        sortArrayList_Data(placeDataArray)
        bookmark_listview.setHasFixedSize(true)
        bookmark_listview.setLayoutManager(LinearLayoutManager(this))
        bookmark_listview.adapter = BookmarkListAdapter(placeDataArray, this,this)
        this.place_array = placeDataArray

    }

    /*function to get the data from the database by call the presenter of this function*/
    fun getData()
    {
        presenter!!.getallBookmarkData(this)
    }

    /*function that sort the array array of the passin value*/
    fun sortArrayList_Data(place_data_array:ArrayList<PlaceData>)
    {
        Collections.sort(place_data_array,object : Comparator<PlaceData>
        {
            override fun compare(p0: PlaceData?, p1: PlaceData?): Int
            {
                val place1 = p0!!.placeName!!.toUpperCase()
                val place2 = p1!!.placeName!!.toUpperCase()

                return place1.compareTo(place2)
            }
        })
    }

    /*function that use to delete data by calling function from presenter*/
    override fun deleteData(place_id:String)
    {
        presenter!!.deleteBookmarkData(this,place_id)
    }

    /*function to start the specify data activity based on user pick*/
    fun displaynextview(position:Int)
    {
        var i : Intent = Intent(applicationContext, ShowNavigationDataPage::class.java)
        i.putExtra("place_data",place_array[position])
        i.putExtra("curlat",currentlatitude)
        i.putExtra("curlng",currentlongtide)
        startActivity(i)
    }

    /*function that create the delete dialog to comfirm user decision*/
    fun createDeleteDialog(placeData: PlaceData):Boolean
    {
        var alertdialogcontroller = AlertDialogController()
        alertdialogcontroller.CreateDeleteBookmarkDialog(this,this,placeData.place_id!!)
        return true
    }

    /*override method used to change the function of the back button on the top left of the app*/
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var id:Int = item!!.itemId

        if(id==android.R.id.home)
        {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    /*function that recheck the data from the data after resume the activity(to check consistency)*/
    override fun onResume() {
        super.onResume()
        if(refreshafterresume)
        {
            getData()
            refreshafterresume=false
        }
    }

    /*function that call when start a new activity and provide consistency check*/
    override fun onPause() {
        super.onPause()
        refreshafterresume=true
    }
}

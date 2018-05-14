package com.example.user.maptest.View.ActivityView

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.AdapterView
import com.example.user.maptest.Model.Asset.PlaceData
import com.example.user.maptest.Presenter.Interface.IDatabaseHelper
import com.example.user.maptest.Presenter.ProcessDataWithRxjava.BookmarkListViewData
import com.example.user.maptest.R
import com.example.user.maptest.Util.AlertDialogController
import com.example.user.maptest.View.Adapter.BookmarkListAdapter
import com.example.user.maptest.View.Adapter.ListViewAdapter
import com.example.user.maptest.View.Interface.IBookmarkListView
import com.google.android.gms.location.places.Place
import kotlinx.android.synthetic.main.activity_bookmark_list_view.*
import kotlinx.android.synthetic.main.activity_listview.*
import java.util.*
import kotlin.collections.ArrayList

class BookmarkListView : AppCompatActivity(),IBookmarkListView {
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

    override fun setadapter(placeDataArray:ArrayList<PlaceData>)
    {
        sortArrayList_Data(placeDataArray)
        bookmark_listview.adapter = BookmarkListAdapter(placeDataArray, this)
        this.place_array = placeDataArray
        bookmark_listview.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            displaynextview(position) })
        bookmark_listview.setOnItemLongClickListener (AdapterView.OnItemLongClickListener{ parent, view, position, id->
            createDeleteDialog(place_array[position])
        })

    }

    fun getData()
    {
        presenter!!.getallBookmarkData(this)
    }

    fun sortArrayList_Data(place_data_array:ArrayList<PlaceData>)
    {
        Collections.sort(place_data_array,object : Comparator<PlaceData>{
            override fun compare(p0: PlaceData?, p1: PlaceData?): Int {
                val place1 = p0!!.placeName!!.toUpperCase()
                val place2 = p1!!.placeName!!.toUpperCase()

                return place1.compareTo(place2)
            }
        })
    }


    override fun deleteData(place_id:String)
    {
        presenter!!.deleteBookmarkData(this,place_id)
    }

    fun displaynextview(position:Int)
    {
        var i : Intent = Intent(applicationContext, ShowNavigationDataPage::class.java)
        i.putExtra("place_data",place_array[position])
        i.putExtra("curlat",currentlatitude)
        i.putExtra("curlng",currentlongtide)
        startActivity(i)
    }

    fun createDeleteDialog(placeData: PlaceData):Boolean
    {
        var alertdialogcontroller = AlertDialogController()
        alertdialogcontroller.CreateDeleteBookmarkDialog(this,this,placeData.place_id!!)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var id:Int = item!!.itemId

        if(id==android.R.id.home)
        {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        if(refreshafterresume)
        {
            getData()
            refreshafterresume=false
        }
    }

    override fun onPause() {
        super.onPause()
        refreshafterresume=true
    }
}

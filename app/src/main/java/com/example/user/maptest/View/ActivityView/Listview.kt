package com.example.user.maptest.View.ActivityView

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import com.example.user.maptest.Model.Asset.PlaceData
import com.example.user.maptest.R
import com.example.user.maptest.View.Adapter.ListViewAdapter
import kotlinx.android.synthetic.main.activity_listview.*

class Listview : AppCompatActivity(){
    lateinit var place_array : ArrayList<PlaceData>
    private var currentlatitude:Double ?=null
    private var currentlongtide:Double ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listview)
        val bundleobj : Bundle = intent.extras
        currentlatitude = intent.getDoubleExtra("curlat",0.1)
        currentlongtide = intent.getDoubleExtra("curlng",0.1)
        place_array = bundleobj.getSerializable("place_array") as ArrayList<PlaceData>
        place_list_view.adapter = ListViewAdapter(place_array, this)
        place_list_view.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            Log.d("position : ",position.toString())
            displaynextview(position) })
    }

    fun displaynextview(position:Int)
    {
        var i : Intent = Intent(applicationContext, ShowNavigationDataPage::class.java)
        i.putExtra("place_data",place_array[position])
        i.putExtra("curlat",currentlatitude)
        i.putExtra("curlng",currentlongtide)
        finish()
        startActivity(i)
    }

}

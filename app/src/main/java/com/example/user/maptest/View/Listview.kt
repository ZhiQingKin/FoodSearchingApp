package com.example.user.maptest.View

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.Toast
import com.example.user.maptest.Model.PlaceData
import com.example.user.maptest.R
import kotlinx.android.synthetic.main.activity_listview.*

class Listview : AppCompatActivity() {
    lateinit var place_array : ArrayList<PlaceData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listview)
        val bundleobj : Bundle = intent.extras
        place_array = bundleobj.getSerializable("place_array") as ArrayList<PlaceData>
        place_list_view.adapter = listviewadapter(place_array,this)

        place_list_view.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id -> Log.d("Position", position.toString()) })
    }
}

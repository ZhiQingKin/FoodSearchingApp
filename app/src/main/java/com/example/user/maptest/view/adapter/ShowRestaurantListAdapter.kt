package com.example.user.maptest.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.user.maptest.model.asset.PlaceData
import com.example.user.maptest.model.geturl.URLGenerator
import com.example.user.maptest.R
import com.example.user.maptest.view.activityview.Listview
import com.example.user.maptest.view.viewholder.RestaurantListViewHolder
import com.squareup.picasso.Picasso

class ShowRestaurantListAdapter: RecyclerView.Adapter<RestaurantListViewHolder>
{
    /*adapter that set the data retreive from the constrcutor to build the view of each recycle view*/
    lateinit var places : ArrayList<PlaceData>
    lateinit var mcontext: Context
    lateinit var urlGenerator: URLGenerator
    lateinit var RestaurantListActicity: Listview


    constructor(places: ArrayList<PlaceData>, context: Context,RestaurantListActicity:Listview)
    {
        this.places = places
        this.mcontext=context
        urlGenerator = URLGenerator()
        this.RestaurantListActicity = RestaurantListActicity
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RestaurantListViewHolder
    {
        val layoutInflater = LayoutInflater.from(mcontext)
        val rowMain = layoutInflater.inflate(R.layout.customlistview,parent,false)
        return RestaurantListViewHolder(rowMain)
    }

    override fun getItemCount(): Int {
        return places.count()//To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(restaurantListHolder: RestaurantListViewHolder?, pos: Int)
    {
        restaurantListHolder!!.PlaceNameText.setText(places[pos].placeName!!)
        restaurantListHolder!!.PlaceAddress.setText(places[pos].placeAddress)
        if (places[pos].photoreference != "error")
        {
            val url: String = urlGenerator.geturl_photoreference(places[pos].photoreference!!)
            Picasso.with(mcontext).load(url).fit().into(restaurantListHolder.RestaurantImage)
        }
        else
        {
            Picasso.with(mcontext).load(R.drawable.errorloadimage).fit().into(restaurantListHolder.RestaurantImage)
        }
        restaurantListHolder!!.ResRating.rating= places[pos].rating!!.toFloat()
        restaurantListHolder!!.Ratingtext.setText(places[pos].rating)
        restaurantListHolder!!.RestaurantLinearLayout.setOnClickListener({
            RestaurantListActicity.displaynextview(pos)
        })
    }

}
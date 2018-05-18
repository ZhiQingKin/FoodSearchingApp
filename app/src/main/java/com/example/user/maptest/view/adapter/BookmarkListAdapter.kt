package com.example.user.maptest.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.user.maptest.model.asset.PlaceData
import com.example.user.maptest.model.geturl.URLGenerator
import com.example.user.maptest.R
import com.example.user.maptest.view.activityview.BookmarkListView
import com.example.user.maptest.view.viewholder.BookmarkRecycleViewHolder
import com.squareup.picasso.Picasso

class BookmarkListAdapter : RecyclerView.Adapter<BookmarkRecycleViewHolder>
{
    /*adapter that set the data retreive from the constrcutor to build the view of each recycle view*/
    lateinit var places : ArrayList<PlaceData>
    lateinit var mcontext: Context
    lateinit var urlGenerator: URLGenerator
    lateinit var BookmarkListActivity: BookmarkListView


    constructor(places: ArrayList<PlaceData>, context: Context,BookmarkListActivity:BookmarkListView)
    {
        this.places = places
        this.mcontext=context
        urlGenerator = URLGenerator()
        this.BookmarkListActivity = BookmarkListActivity
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BookmarkRecycleViewHolder
    {
        val layoutInflater = LayoutInflater.from(mcontext)
        val rowMain = layoutInflater.inflate(R.layout.bookmarklayout,parent,false)
        return BookmarkRecycleViewHolder(rowMain)
    }

    override fun getItemCount(): Int
    {
        return places.count()
    }

    override fun onBindViewHolder(bookmarkListHolder: BookmarkRecycleViewHolder?, pos: Int)
    {
        bookmarkListHolder!!.PlaceNameText.text = places[pos].placeName!!
        bookmarkListHolder!!.PlaceAddress.text = places[pos].placeAddress
        if (places[pos].photoreference != "error") {
            val url: String = urlGenerator.geturl_photoreference(places[pos].photoreference!!)
            Picasso.with(mcontext).load(url).fit().into(bookmarkListHolder!!.RestaurantImage)
        } else {
            Picasso.with(mcontext).load(R.drawable.errorloadimage).fit().into(bookmarkListHolder!!.RestaurantImage)
        }
        bookmarkListHolder!!.ResRating.rating = places[pos].rating!!.toFloat()
        bookmarkListHolder!!.Ratingtext.text = places[pos].rating
        bookmarkListHolder!!.BookmarkLayout.setOnClickListener({
            BookmarkListActivity.displaynextview(pos)
        })
        bookmarkListHolder!!.BookmarkLayout.setOnLongClickListener({
            BookmarkListActivity.createDeleteDialog(places[pos])
        })
    }


}
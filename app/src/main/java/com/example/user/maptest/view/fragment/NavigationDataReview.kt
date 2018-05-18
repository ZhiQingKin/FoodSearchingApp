package com.example.user.maptest.view.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.user.maptest.model.asset.Review

import com.example.user.maptest.R
import com.example.user.maptest.view.adapter.ReviewViewAdapter
import kotlinx.android.synthetic.main.activity_bookmark_list_view.*


class NavigationDataReview : Fragment()
{
    var reviews: ArrayList<Review> = ArrayList<Review>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        reviews = arguments.getSerializable("reviews") as ArrayList<Review>    // retreive data from the parent activity
        var view: View? = inflater.inflate(R.layout.fragment_navigation_data_review, container, false)
        val ReviewList = view!!.findViewById<RecyclerView>(R.id.reviewList)
        ReviewList.setHasFixedSize(true)
        ReviewList.setLayoutManager(LinearLayoutManager(context))
        var reviewadapter: ReviewViewAdapter = ReviewViewAdapter(reviews, context) //set the adapter of the recycle view
        ReviewList.adapter = reviewadapter
        return view
    }


}

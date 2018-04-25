package com.example.user.maptest.View.Fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.user.maptest.Model.Asset.Review

import com.example.user.maptest.R
import com.example.user.maptest.View.Adapter.ReviewViewAdapter


class NavigationDataReview : Fragment() {
    var reviews: ArrayList<Review> = ArrayList<Review>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        reviews = arguments.getSerializable("reviews") as ArrayList<Review>
        var view: View? = inflater.inflate(R.layout.fragment_navigation_data_review, container, false)
        val ReviewList = view!!.findViewById<ListView>(R.id.reviewList)
        var reviewadapter: ReviewViewAdapter = ReviewViewAdapter(reviews, context)
        ReviewList.adapter = reviewadapter
        return view
    }


}

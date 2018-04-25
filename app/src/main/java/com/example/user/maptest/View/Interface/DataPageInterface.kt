package com.example.user.maptest.View.Interface

import com.example.user.maptest.Model.Asset.Review

interface DataPageInterface {
    fun CreateAdapter(ImageContainer: ArrayList<String?>)
    fun DisplayNavigationPage()
    fun init_Threat_For_Duration_Driving()
    fun init_Threat_For_Duration_Walking()
    fun checkdriving(duration: String?, distance: String?)
    fun checkwalking(duration: String?)
    fun displayDuration()
    fun displayOpening(openingArray: ArrayList<String>)
    fun displayReview(reviewArray: ArrayList<Review>)
}
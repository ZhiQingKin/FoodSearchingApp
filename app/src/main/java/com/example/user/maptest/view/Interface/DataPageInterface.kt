package com.example.user.maptest.view.Interface

import com.example.user.maptest.model.asset.Review

interface DataPageInterface
{
    //Create the adapter for converting the list of image url into image and store it inside the view pager
    fun CreateAdapter(ImageContainer: ArrayList<String?>)
    //function that create a new intent for the next activity(navigation activity)
    fun DisplayNavigationPage()
    //function that init the threat for executing the function for getting duration for driving
    fun init_Threat_For_Duration_Driving()
    //function that init the threat for executing the function for getting duration for walking
    fun init_Threat_For_Duration_Walking()
    //function that check the value of the driving duration data and set it
    fun checkdriving(duration: String?, distance: String?)
    //function that check the value of the walking duration data and set it
    fun checkwalking(duration: String?)
    //function that display the duration into the viewpager duration fragment
    fun displayDuration()
    //function that display the opening time into the viewpager opening time fragment
    fun displayOpening(openingArray: ArrayList<String?>)
    //function that display the review data into the viewpager review page fragment
    fun displayReview(reviewArray: ArrayList<Review>)
}
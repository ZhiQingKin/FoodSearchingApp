package com.example.user.maptest.view.Interface

interface ShowDirectionInterface
{
    /*function that start the threat for retriving the direction data from the google navigation web services*/
    fun StartDirectionSearch()
    /*function that use the data from the google navigation web services and display it into the map*/
    public fun displayDirection(directionsList: ArrayList<String>)
}
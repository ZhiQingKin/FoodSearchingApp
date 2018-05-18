package com.example.user.maptest.view.Interface

interface DataPageOverviewInterface
{
    //control the bookmark icon (on/off)
    fun showdata(boolean: Boolean)
    //retrive and check the data from the database
    fun checkData()
    /*delete the bookmark data from the database*/
    fun deleteBookmark()
    /*add the bookmark data into the database*/
    fun addBookmark()
}
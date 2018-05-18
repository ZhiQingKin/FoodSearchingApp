package com.example.user.maptest.util

import android.app.Service
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class CheckInternetConnection
{
    /*class that used to check the internet connection within the app*/
    var context:Context ?=null

    constructor(context: Context)
    {
        this.context=context
    }

    /*get the connection of the mobile with the system service and return a boolean to check the condition*/
    public fun getConnection():Boolean
    {
        var connectivity: ConnectivityManager? = context!!.getSystemService(Service.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(connectivity!=null)
        {
            var info: NetworkInfo? = connectivity.activeNetworkInfo
            if(info!=null)
            {
                if(info.state == NetworkInfo.State.CONNECTED)
                {
                    return true
                }
            }
        }
        return false
    }
}
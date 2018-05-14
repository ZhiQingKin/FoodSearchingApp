package com.example.user.maptest.Util

import android.app.Service
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class CheckInternetConnection {
    var context:Context ?=null

    constructor(context: Context)
    {
        this.context=context
    }
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
package com.example.user.maptest.model.geturl

import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import java.io.IOException


class DownloadURL
{

    /*OkHTTP class that use to generate the json data from specify url*/

    /*read the url and change it to json data*/
    @Throws(IOException::class)
    fun readUrl(myUrl: String): String?
    {
        var Client : OkHttpClient = OkHttpClient()
        var req : Request = Request.Builder().url(myUrl).build()
        var res : Response ?= null
        var data:String ?=null
        try
        {
            res = Client.newCall(req).execute()
            data = res.body().string()
        }
        catch (e:IOException)
        {
            e.printStackTrace()
        }
        return data

    }
}
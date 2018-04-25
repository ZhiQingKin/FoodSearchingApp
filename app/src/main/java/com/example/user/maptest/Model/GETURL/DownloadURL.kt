package com.example.user.maptest.Model.GETURL

import com.example.user.maptest.Model.Interface.Model
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import java.io.IOException

/**
 * @author Priyanka
 */


class DownloadURL : Model {


    @Throws(IOException::class)
    override fun readUrl(myUrl: String): String? {
        var Client : OkHttpClient = OkHttpClient()
        var req : Request = Request.Builder().url(myUrl).build()
        var res : Response ?= null
        var data:String ?=null
        try {
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
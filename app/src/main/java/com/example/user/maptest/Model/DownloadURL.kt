package com.example.user.maptest.Model

import android.util.Log

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * @author Priyanka
 */


class DownloadURL {


    @Throws(IOException::class)
    fun readUrl(myUrl: String): String? {
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
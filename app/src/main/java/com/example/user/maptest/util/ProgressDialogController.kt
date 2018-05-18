package com.example.user.maptest.util

import android.app.ProgressDialog
import android.content.Context

class ProgressDialogController
{
    /* class that control the progress dialog of the app*/
    lateinit var progressDialog: ProgressDialog

    constructor(context: Context)
    {
        progressDialog = ProgressDialog(context)
        progressDialog.setCancelable(false)
        progressDialog.setTitle("Loading...")
    }

    fun progressBarShow()
    {
        progressDialog.show()
    }

    fun progressBarHide()
    {
        progressDialog.hide()
    }
}
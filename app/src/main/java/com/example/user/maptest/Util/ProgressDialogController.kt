package com.example.user.maptest.Util

import android.app.ProgressDialog
import android.content.Context

class ProgressDialogController {
    lateinit var progressDialog: ProgressDialog

    constructor(context: Context) {
        progressDialog = ProgressDialog(context)
        progressDialog.setCancelable(false)
        progressDialog.setTitle("Loading...")
    }

    fun progressBarShow() {
        progressDialog.show()
    }

    fun progressBarHide() {
        progressDialog.hide()
    }
}
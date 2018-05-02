package com.example.user.maptest.Util

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import com.example.user.maptest.R
import com.example.user.maptest.View.Interface.IBookmarkListView


class AlertDialogController {

    public fun CreateExitDialog(context: Context)
    {
        val exitdialog = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val dialogcontent = inflater.inflate(R.layout.exit_alert_dialog, null)
        exitdialog.setView(dialogcontent)
        exitdialog.setCancelable(false)
        var alertDialog:AlertDialog = exitdialog.create()
        alertDialog.show()

        val closeBtn = dialogcontent.findViewById<Button>(R.id.CloseBtn)
        closeBtn.setOnClickListener({
            System.exit(0)
        })

        val backToAppBtn = dialogcontent.findViewById<Button>(R.id.returnBtn)
        backToAppBtn.setOnClickListener({
            alertDialog.dismiss()
        })
    }


    public fun CreateDeleteBookmarkDialog(context: Context,view:IBookmarkListView,place_id:String)
    {
        val exitdialog = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val dialogcontent = inflater.inflate(R.layout.deletebookmarkalertdialog, null)
        exitdialog.setView(dialogcontent)
        exitdialog.setCancelable(false)
        var alertDialog:AlertDialog = exitdialog.create()
        alertDialog.show()

        val YesBTN = dialogcontent.findViewById<Button>(R.id.bookmarkdeleteyes)
        YesBTN.setOnClickListener({
            view.deleteData(place_id)
            (context as Activity).recreate()
        })

        val NoBTN = dialogcontent.findViewById<Button>(R.id.bookmarkdeleteno)
        NoBTN.setOnClickListener({
            alertDialog.dismiss()
        })
    }
}
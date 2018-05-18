package com.example.user.maptest.util

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import com.example.user.maptest.R
import com.example.user.maptest.view.Interface.IBookmarkListView


class AlertDialogController
{
    /*class that create the alert dialog of the app*/

    /*function that create the exit dialog for use to comfirm the user decision when exiting the app*/
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

    /*function that create the alert diaload that comfirm the decision of the user to delete the bookmark of the database*/
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
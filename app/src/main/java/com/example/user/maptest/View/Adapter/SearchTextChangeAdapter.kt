package com.example.user.maptest.View.Adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.ImageView

class SearchTextChangeAdapter : TextWatcher{
    var autosearchtv:AutoCompleteTextView ?=null
    var cancelImage:ImageView ?=null

    constructor(autosearchtv: AutoCompleteTextView?, cancelImage: ImageView?) {
        this.autosearchtv = autosearchtv
        this.cancelImage = cancelImage
    }

    override fun afterTextChanged(p0: Editable?) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
         //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int)
    {
        if(autosearchtv!!.text.length>0)
        {
            cancelImage!!.visibility = View.VISIBLE
        }
        else
        {
            cancelImage!!.visibility = View.GONE
        }
    }
}
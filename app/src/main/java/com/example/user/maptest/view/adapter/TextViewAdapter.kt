package com.example.user.maptest.view.adapter

import android.content.Context
import android.view.KeyEvent
import android.widget.TextView
import android.view.inputmethod.EditorInfo
import com.example.user.maptest.view.Interface.MainViewInterface
import android.view.inputmethod.InputMethodManager


class TextViewAdapter:TextView.OnEditorActionListener
{
    var mainViewInterface:MainViewInterface ?=null
    constructor(mainViewInterface: MainViewInterface)
    {
        this.mainViewInterface = mainViewInterface
    }
    /*reset the button listener of the mobile keyboard and make the button do a search function*/
    override fun onEditorAction(textView: TextView?, actionId: Int, keyEvent: KeyEvent?): Boolean
    {
        if(actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_DONE
                || keyEvent!!.getAction() == KeyEvent.ACTION_DOWN
                || keyEvent!!.getAction() == KeyEvent.KEYCODE_ENTER)
        {

            //execute our method for searching
            mainViewInterface!!.geoLocate()
            val imm = textView!!.getContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(textView.getWindowToken(), 0)
        }

        return false;
    }
}
package com.example.user.maptest.View.Adapter

import android.content.Context
import android.view.KeyEvent
import android.widget.TextView
import android.view.inputmethod.EditorInfo
import com.example.user.maptest.View.Interface.MainViewInterface
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager


class TextViewAdapter:TextView.OnEditorActionListener {
    var mainViewInterface:MainViewInterface ?=null
    constructor(mainViewInterface: MainViewInterface)
    {
        this.mainViewInterface = mainViewInterface
    }
    override fun onEditorAction(textView: TextView?, actionId: Int, keyEvent: KeyEvent?): Boolean {
        if(actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_DONE
                || keyEvent!!.getAction() == KeyEvent.ACTION_DOWN
                || keyEvent!!.getAction() == KeyEvent.KEYCODE_ENTER){

            //execute our method for searching
            mainViewInterface!!.geoLocate()
            val imm = textView!!.getContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(textView.getWindowToken(), 0)
        }

        return false;
    }
}
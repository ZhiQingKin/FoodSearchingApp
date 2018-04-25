package com.example.user.maptest.View.Fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.user.maptest.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class NavigationDataOpeningTime : Fragment() {

    lateinit var OpeningTime: ArrayList<String>
    var fullcontext: String = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        OpeningTime = arguments.getStringArrayList("opening")
        var view: View? = inflater.inflate(R.layout.fragment_navigation_data_opening_time, container, false)
        for (i in 0..OpeningTime.count() - 1) {
            fullcontext += OpeningTime[i] + "\n"
        }

        val ot1 = view!!.findViewById<TextView>(R.id.Textopening1)
        ot1.text = fullcontext



        return view
    }


}

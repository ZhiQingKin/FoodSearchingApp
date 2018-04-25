package com.example.user.maptest.View.Fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
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
class NavigationDataOverview : Fragment() {
    lateinit var place_name: String
    lateinit var place_address: String
    var rating: Float? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        place_name = arguments.getString("place_name")
        place_address = arguments.getString("place_address")
        rating = arguments.getFloat("place_rating")

        var view: View? = inflater.inflate(R.layout.fragment_navigation_data_overview, container, false)

        val NameTv = view!!.findViewById<TextView>(R.id.TextPlaceName)
        NameTv.text = "Name : \n" + place_name

        val Addresstv = view!!.findViewById<TextView>(R.id.TextAddress)
        Addresstv.text = "Address : \n" + place_address

        val ratingtv = view!!.findViewById<TextView>(R.id.TextRating)
        ratingtv.text = "Rating : \n" + rating

        val ratingBar = view!!.findViewById<RatingBar>(R.id.ResRating2)
        ratingBar.rating = rating as Float
        return view
    }


}

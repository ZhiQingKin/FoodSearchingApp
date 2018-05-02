package com.example.user.maptest.View.Fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import com.example.user.maptest.Model.Asset.PlaceData
import com.example.user.maptest.Model.DatabaseModel.LocationDB
import com.example.user.maptest.Presenter.Interface.IDatabaseHelper
import com.example.user.maptest.Presenter.ProcessDataWithRxjava.NavigationOverviewPresenter
import com.example.user.maptest.R
import com.example.user.maptest.View.Interface.DataPageOverviewInterface
import kotlinx.android.synthetic.main.fragment_navigation_data_overview.*
import kotlinx.android.synthetic.main.fragment_navigation_data_overview.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class NavigationDataOverview : Fragment(),DataPageOverviewInterface {
    lateinit var place_name: String
    lateinit var place_address: String
    lateinit var placeData: PlaceData
    var rating: Float? = null
    lateinit var iDatabaseHelper:IDatabaseHelper
    var withinDatabase:Boolean =false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        place_name = arguments.getString("place_name")
        place_address = arguments.getString("place_address")
        rating = arguments.getFloat("place_rating")
        placeData = arguments.getSerializable("place_Data") as PlaceData
        iDatabaseHelper = NavigationOverviewPresenter(this.context,this)

        var view: View? = inflater.inflate(R.layout.fragment_navigation_data_overview, container, false)

        val NameTv = view!!.findViewById<TextView>(R.id.TextPlaceName)
        NameTv.text = "Name : \n" + place_name

        val Addresstv = view!!.findViewById<TextView>(R.id.TextAddress)
        Addresstv.text = "Address : \n" + place_address

        val ratingtv = view!!.findViewById<TextView>(R.id.TextRating)
        ratingtv.text = "Rating : \n" + rating

        val ratingBar = view!!.findViewById<RatingBar>(R.id.ResRating2)
        ratingBar.rating = rating as Float
        checkData()

        view!!.bookmarkButton.setOnClickListener({
            if(withinDatabase)
            {
                deleteBookmark()
                Log.d("deleted","deleted")
                withinDatabase=false
                view!!.bookmarkButton.background = resources.getDrawable(R.drawable.bookmarkoff)
            }
            else
            {
                addBookmark()
                Log.d("added","added")
                withinDatabase=true
                view!!.bookmarkButton.background = resources.getDrawable(R.drawable.bookmarkon)
            }
        })

        return view
    }

    override fun addBookmark()
    {
        iDatabaseHelper.addBookmarkData(this.context,placeData)
    }


    override fun deleteBookmark()
    {
        iDatabaseHelper.deleteBookmarkData(this.context,placeData.place_id!!)
    }

    override fun checkData()
    {
        iDatabaseHelper.checkBookmarkData(this.context,placeData.place_id!!)
    }


    override fun showdata(boolean: Boolean)
    {
        if(boolean)
        {
            view!!.bookmarkButton.background = resources.getDrawable(R.drawable.bookmarkon)
            withinDatabase=true
        }
        else
        {
            view!!.bookmarkButton.background = resources.getDrawable(R.drawable.bookmarkoff)
            withinDatabase=false
        }
    }


}

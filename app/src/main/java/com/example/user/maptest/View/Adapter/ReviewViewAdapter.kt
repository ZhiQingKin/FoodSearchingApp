package com.example.user.maptest.View.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.example.user.maptest.Model.Asset.Review
import com.example.user.maptest.R
import com.example.user.maptest.View.ViewHolder.ReviewListHolder
import com.squareup.picasso.Picasso

class ReviewViewAdapter : BaseAdapter {
    var reviews: ArrayList<Review> = ArrayList<Review>()
    lateinit var mcontext: Context

    constructor(reviews: ArrayList<Review>, context: Context) {
        this.reviews = reviews
        this.mcontext = context
    }


    override fun getView(pos: Int, view: View?, viewG: ViewGroup?): View {
        var rowMain = view
        var reviewListHolder:ReviewListHolder ?=null

        if(rowMain==null)
        {
            val layoutInflater = LayoutInflater.from(mcontext)
            rowMain = layoutInflater.inflate(R.layout.reviewlayout, viewG, false)
            reviewListHolder = ReviewListHolder(rowMain)
            rowMain!!.tag = reviewListHolder
        }
        else
        {
            reviewListHolder = rowMain!!.tag as ReviewListHolder
        }


        Picasso.get().load(reviews[pos].photo_url).fit().into(reviewListHolder.Profile_Pic)
        reviewListHolder.UserName.text = reviews[pos].username
        reviewListHolder.LastComentDuration.text = reviews[pos].commenttime
        reviewListHolder.CommentInfo.text = reviews[pos].usercomment + "\n"
        reviewListHolder.UserRating.rating = reviews[pos].userrating!!


        return rowMain
    }

    override fun getItem(pos: Int): Any {
        return reviews[pos]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return reviews.count()
    }
}
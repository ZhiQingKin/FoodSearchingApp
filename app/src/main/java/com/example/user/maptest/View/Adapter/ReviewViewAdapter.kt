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
import com.squareup.picasso.Picasso

class ReviewViewAdapter : BaseAdapter {
    var reviews: ArrayList<Review> = ArrayList<Review>()
    lateinit var mcontext: Context

    constructor(reviews: ArrayList<Review>, context: Context) {
        this.reviews = reviews
        this.mcontext = context
    }


    override fun getView(pos: Int, view: View?, viewG: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(mcontext)
        val rowMain = layoutInflater.inflate(R.layout.reviewlayout, viewG, false)
        val Profile_Pic = rowMain.findViewById<ImageView>(R.id.profilepic)
        Picasso.get().load(reviews[pos].photo_url).fit().into(Profile_Pic)
        val UserName = rowMain.findViewById<TextView>(R.id.username)
        UserName.text = reviews[pos].username
        val LastComentDuration = rowMain.findViewById<TextView>(R.id.commentperoid)
        LastComentDuration.text = reviews[pos].commenttime
        val CommentInfo = rowMain.findViewById<TextView>(R.id.commentinfo)
        CommentInfo.text = reviews[pos].usercomment + "\n"
        val UserRating = rowMain.findViewById<RatingBar>(R.id.RatingBarUser)
        UserRating.rating = reviews[pos].userrating!!
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
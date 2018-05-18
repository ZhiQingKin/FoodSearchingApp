package com.example.user.maptest.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.user.maptest.model.asset.Review
import com.example.user.maptest.R
import com.example.user.maptest.view.viewholder.ReviewRecycleViewHolder
import com.squareup.picasso.Picasso

class ReviewViewAdapter : RecyclerView.Adapter<ReviewRecycleViewHolder>
{
    /*adapter that set the data retreive from the constrcutor to build the view of each recycle view*/
    var reviews: ArrayList<Review> = ArrayList<Review>()
    lateinit var mcontext: Context

    constructor(reviews: ArrayList<Review>, context: Context)
    {
        this.reviews = reviews
        this.mcontext = context
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ReviewRecycleViewHolder
    {

        val layoutInflater = LayoutInflater.from(mcontext)
        val rowMain = layoutInflater.inflate(R.layout.reviewlayout, parent, false)
        return ReviewRecycleViewHolder(rowMain)
    }

    override fun getItemCount(): Int
    {
        return reviews.count()
    }

    override fun onBindViewHolder(reviewListHolder: ReviewRecycleViewHolder?, pos: Int)
    {
        Picasso.with(mcontext).load(reviews[pos].photo_url).fit().into(reviewListHolder!!.Profile_Pic)
        reviewListHolder!!.UserName.text = reviews[pos].username
        reviewListHolder!!.LastComentDuration.text = reviews[pos].commenttime
        reviewListHolder!!.CommentInfo.text = reviews[pos].usercomment + "\n"
        reviewListHolder!!.UserRating.rating = reviews[pos].userrating!!
    }


}
package com.example.user.maptest.View.ViewHolder

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.example.user.maptest.R

class ReviewListHolder {
    lateinit var Profile_Pic:ImageView
    lateinit var UserName:TextView
    lateinit var LastComentDuration:TextView
    lateinit var CommentInfo:TextView
    lateinit var UserRating:RatingBar


    constructor(rowMain:View)
    {
        Profile_Pic = rowMain.findViewById<ImageView>(R.id.profilepic)
        UserName = rowMain.findViewById<TextView>(R.id.username)
        LastComentDuration = rowMain.findViewById<TextView>(R.id.commentperoid)
        CommentInfo = rowMain.findViewById<TextView>(R.id.commentinfo)
        UserRating = rowMain.findViewById<RatingBar>(R.id.RatingBarUser)
    }
}
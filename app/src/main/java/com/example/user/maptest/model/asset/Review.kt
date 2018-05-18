package com.example.user.maptest.model.asset

import java.io.Serializable

class Review :Serializable
{
    /*class that contain all the review data needed in the app*/
    var username : String ?=null
    var userrating : Float ?=null
    var usercomment : String ?=null
    var photo_url : String ?=null
    var commenttime : String ?=null

    constructor(username: String?, userrating: Float?, usercomment: String?, photo_url: String?, commenttime: String?)
    {
        this.username = username
        this.userrating = userrating
        this.usercomment = usercomment
        this.photo_url = photo_url
        this.commenttime = commenttime
    }
}
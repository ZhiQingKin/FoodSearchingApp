package com.example.user.maptest.Model.Asset

import java.io.Serializable

class Review :Serializable{
    var username : String ?=null
    var userrating : Float ?=null
    var usercomment : String ?=null
    var photo_url : String ?=null
    var commenttime : String ?=null

    constructor(username: String?, userrating: Float?, usercomment: String?, photo_url: String?, commenttime: String?) {
        this.username = username
        this.userrating = userrating
        this.usercomment = usercomment
        this.photo_url = photo_url
        this.commenttime = commenttime
    }
}
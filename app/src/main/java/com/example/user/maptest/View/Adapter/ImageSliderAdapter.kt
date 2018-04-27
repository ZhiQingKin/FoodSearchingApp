package com.example.user.maptest.View.Adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.user.maptest.R
import com.squareup.picasso.Picasso

class ImageSliderAdapter : PagerAdapter {
    lateinit var context: Context
    lateinit var UrlContainer: ArrayList<String?>

    constructor(context: Context, UrlContainer: ArrayList<String?>) : super() {
        this.context = context
        this.UrlContainer = UrlContainer
    }


    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        return view == `object`
    }


    override fun getCount(): Int {
        return UrlContainer.count()
    }


    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        var imageView: ImageView = ImageView(context)
        if (UrlContainer[position] == "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=error&key=AIzaSyAoJHWDnTqRGbNfVKT2XdxOcQShDDlgndQ") {
            Picasso.get()
                    .load(R.drawable.errorloadimage)
                    .fit()
                    .into(imageView)
            container!!.addView(imageView)
        } else {
            Picasso.get()
                    .load(UrlContainer[position])
                    .fit()
                    .into(imageView)
            container!!.addView(imageView)
        }

        return imageView
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        container!!.removeView(`object` as View)
    }
}
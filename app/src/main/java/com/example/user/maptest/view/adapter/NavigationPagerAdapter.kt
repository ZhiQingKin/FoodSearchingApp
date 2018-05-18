package com.example.user.maptest.view.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class NavigationPagerAdapter : FragmentPagerAdapter
{
    /*adapter that that control the fragment of the viewpager*/
    constructor(fm: FragmentManager?) : super(fm)

    private var fragmentlist: ArrayList<Fragment> = ArrayList<Fragment>()
    private var fragmentname: ArrayList<String> = ArrayList<String>()


    override fun getItem(position: Int): Fragment
    {
        return fragmentlist[position]
    }

    override fun getCount(): Int
    {
        return fragmentlist.count()
    }

    override fun getPageTitle(position: Int): CharSequence
    {
        return fragmentname.get(position)
    }

    fun AddFragment(fragment: Fragment, name: String)
    {
        fragmentlist.add(fragment)
        fragmentname.add(name)
    }
}
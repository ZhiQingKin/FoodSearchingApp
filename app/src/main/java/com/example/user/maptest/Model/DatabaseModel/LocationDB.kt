package com.example.user.maptest.Model.DatabaseModel

import android.content.Context
import android.location.Address
import android.util.Log
import com.example.user.maptest.Model.Asset.BookmarkLocation
import com.example.user.maptest.Model.Asset.PlaceData
import com.example.user.maptest.Model.Interface.Model
import com.google.android.gms.location.places.Place
import com.google.android.gms.maps.model.LatLng
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class LocationDB :Model{
    lateinit var realm:Realm
    lateinit var realmConfiguration:RealmConfiguration

    constructor(context: Context)
    {
        Realm.init(context)
        realmConfiguration= RealmConfiguration
                                .Builder()
                                .deleteRealmIfMigrationNeeded()
                                .name(Realm.DEFAULT_REALM_NAME)
                                .schemaVersion(1)
                                .build()
        Realm.setDefaultConfiguration(realmConfiguration)
    }

    override fun addBookmarkLocation(context: Context,placeData: PlaceData) {
        realm = Realm.getDefaultInstance()

        realm.executeTransaction({
            var result : Number? = realm.where<BookmarkLocation>().max("id")
            val bookmarkLocation: BookmarkLocation
            if(result!=null)
            {
                bookmarkLocation=realm.createObject<BookmarkLocation>(result.toInt()+1)
            }
            else
            {
                bookmarkLocation=realm.createObject<BookmarkLocation>(1)
            }
            bookmarkLocation.lat = placeData.lat.toString()
            bookmarkLocation.lng = placeData.lng.toString()
            bookmarkLocation.photoreference= placeData.photoreference!!
            bookmarkLocation.placeAddress = placeData.placeAddress!!
            bookmarkLocation.place_id=placeData.place_id!!
            bookmarkLocation.rating = placeData.rating!!
            bookmarkLocation.LocationName=placeData.placeName!!
        })
        realm.close()

    }

    override fun getBookmarkLocation(context: Context) :ArrayList<PlaceData>{
        realm = Realm.getDefaultInstance()
        var result : RealmResults<BookmarkLocation> = realm.where(BookmarkLocation::class.java).findAll()
        var arrayList = ArrayList<PlaceData>()

        for (i in 0..result.count()-1)
        {
            arrayList.add(PlaceData(result[i]!!.LocationName,
                    result[i]!!.placeAddress,
                    result[i]!!.lat.toDouble(),
                    result[i]!!.lng.toDouble(),
                    result[i]!!.rating,
                    result[i]!!.place_id,
                    result[i]!!.photoreference))
        }
        realm.close()
        return arrayList
    }

    override fun deleteBookmarkLocationAll(context: Context) {
        realm = Realm.getDefaultInstance()
        realm.executeTransaction (
                {
                    realm.deleteAll()
                }
        )
        realm.close()
    }

    override fun getBookmarkLocation(context: Context,place_id: String):Boolean
    {
        realm = Realm.getDefaultInstance()
        var result : BookmarkLocation? = realm.where<BookmarkLocation>().equalTo("place_id",place_id).findFirst()
        if(result!=null)
        {
            Log.d(result.LocationName,result.place_id)
            realm.close()
            return true
        }
        else
        {
            realm.close()
            return false
        }
    }


    override fun deleteBookmarkLocation(context: Context, place_id: String) {
        realm = Realm.getDefaultInstance()
        try {
            realm.executeTransaction({
                var result : BookmarkLocation? = realm.where<BookmarkLocation>().equalTo("place_id",place_id).findFirst()
                if (result!=null)
                {
                    result.deleteFromRealm()
                }
            })
        }finally {
            realm.close()
        }
    }

}
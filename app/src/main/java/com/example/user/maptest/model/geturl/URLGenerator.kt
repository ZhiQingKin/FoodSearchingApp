package com.example.user.maptest.model.geturl

class URLGenerator
{
    /*class that provide all the function to generate correct url for the app*/

    /*generate the url of the place api place nearby place (not specify data)*/
    public fun getPlaceUrl(latitude: Double, longitude: Double, nearbyPlace: String, PROXIMITY_RADIUS: Double): String
    {

        val googlePlaceUrl = StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?")
        googlePlaceUrl.append("location=$latitude,$longitude")
        googlePlaceUrl.append("&radius=$PROXIMITY_RADIUS")
        googlePlaceUrl.append("&type=$nearbyPlace")
        googlePlaceUrl.append("&sensor=true")
        googlePlaceUrl.append("&key=" + "AIzaSyC3L5cQpZH1iEMbKQZICebrDDfGaxDiSNI")

        return googlePlaceUrl.toString()
    }

    /*function that generate the url of the google navigation data*/
    public fun getDirectionsUrl(latitude: Double, longitude: Double, locationlat: Double, locationlng: Double): String
    {
        val googleDirectionsUrl = StringBuilder("https://maps.googleapis.com/maps/api/directions/json?")
        googleDirectionsUrl.append("origin=$latitude,$longitude")
        googleDirectionsUrl.append("&destination=$locationlat,$locationlng")
        googleDirectionsUrl.append("&key=" + "AIzaSyA1916xA7UxvWV-aQbkgZ_vMrnwAxzW8dA")

        return googleDirectionsUrl.toString()
    }

    /*function that generate the place photo api through photo reference*/
    public fun geturl_photoreference(photoreference: String): String
    {
        val googlephotoUrl = StringBuilder("https://maps.googleapis.com/maps/api/place/photo?")
        googlephotoUrl.append("maxwidth=400")
        googlephotoUrl.append("&photoreference=$photoreference")
        googlephotoUrl.append("&key=" + "AIzaSyAoJHWDnTqRGbNfVKT2XdxOcQShDDlgndQ")
        return googlephotoUrl.toString()
    }

    /*function that generate the url of the specify place data of one place(restaurant)*/
    public fun geturl_place_detail(place_id: String): String
    {
        val googlephotoUrl = StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?")
        googlephotoUrl.append("placeid=$place_id")
        googlephotoUrl.append("&key=" + "AIzaSyC3L5cQpZH1iEMbKQZICebrDDfGaxDiSNI")
        return googlephotoUrl.toString()
    }

    /*function that generate a set of photo url by using a set of photo reference that obtain from specify place data*/
    public fun get_url_all_photoreference(ImageContainer: ArrayList<String?>): ArrayList<String?>
    {
        var OutputResult: ArrayList<String?> = ArrayList<String?>()

        for (i in 0..ImageContainer.count() - 1)
        {
            val googlephotoUrl = StringBuilder("https://maps.googleapis.com/maps/api/place/photo?")
            googlephotoUrl.append("maxwidth=400")
            googlephotoUrl.append("&photoreference=" + ImageContainer[i])
            googlephotoUrl.append("&key=" + "AIzaSyAoJHWDnTqRGbNfVKT2XdxOcQShDDlgndQ")
            OutputResult.add(googlephotoUrl.toString())
        }
        return OutputResult
    }

    /*function that generate the google navigation url with mode(eg:driving,walking,bus,cycling)*/
    public fun getDirectionsUrlMode(currentlatitude: Double, currentlongtide: Double, locationlat: Double?, locationlng: Double?, mode: String): String
    {
        val googleDirectionsUrl = StringBuilder("https://maps.googleapis.com/maps/api/directions/json?")
        googleDirectionsUrl.append("origin=$currentlatitude,$currentlongtide")
        googleDirectionsUrl.append("&destination=$locationlat,$locationlng")
        googleDirectionsUrl.append("&mode=$mode")
        googleDirectionsUrl.append("&key=" + "AIzaSyA1916xA7UxvWV-aQbkgZ_vMrnwAxzW8dA")

        return googleDirectionsUrl.toString()
    }
}
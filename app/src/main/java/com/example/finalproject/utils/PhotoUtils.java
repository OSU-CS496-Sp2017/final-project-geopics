package com.example.finalproject.utils;

import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.text.TextUtils;

import com.example.finalproject.BuildConfig;
import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by Frog on 6/13/17.
 */

public class PhotoUtils {
    private static final String FLICKR_API_BASE_URL = "https://api.flickr.com/services/rest/";
    private static final String FLICKR_API_METHOD_PARAM = "method";
    private static final String FLICKR_API_SEARCH_METHOD = "flickr.photos.search";
    private static final String FLICKR_API_KEY_PARAM = "api_key";
    private static final String FLICKR_API_FORMAT_PARAM = "format";
    private static final String FLICKR_API_FORMAT_JSON = "json";
    private static final String FLICKR_API_NOJSONCALLBACK_PARAM = "nojsoncallback";
    private static final String FLICKR_API_NOJSONCALLBACK = "1";
    private static final String FLICKR_API_EXTRAS_PARAM = "extras";
    private static final String[] FLICKR_API_EXTRAS = {"url_l", "url_m", "owner_name", "description", "date_upload", "geo", "tags"};

    // Added parameters: text=landscape&media=photos&lat=44.571082&lon=-123.27871&radius=1
    private static final String FLICKR_API_TAG_PARAM = "tag";
    private static final String FLICKR_API_TAG = "landscape, building, architecture, nature, -kid";

    private static final String FLICKR_API_MEDIA_PARAM = "media";
    private static final String FLICKR_API_MEDIA_TYPE = "photos";

    private static final String FLICKR_API_LAT_PARAM = "lat";
    private static final String FLICKR_API_LON_PARAM = "lon";

    private static final String FLICKR_API_SORT_PARAM = "sort";

    private static final String FLICKR_API_RADIUS_PARAM = "radius";
    private static final String FLICKR_API_RADIUS = "5"; // Default is km

    private static double currentLat = 44.57;
    private static double currentLon = -123.27;


    private static final String FLICKR_API_KEY = BuildConfig.FLICKR_API_KEY;

    private static final Gson gson = new Gson();

    public static void setCurrentLocation (double lat, double lon) {
        currentLat = lat;
        currentLon = lon;
    }

    public static class FlickrExploreResults {
        FlickrPhotos photos;
        String stat;
    }

    public static class FlickrPhotos {
        FlickrPhoto[] photo;
    }

    public static class FlickrPhoto implements Serializable {
        public String title;
        public String ownername;
        public String date_upload;
        public String latitude;
        public String longitude;
        public String tags;
        public String url_l;
        public String url_m;
        public int width_l;
        public int height_l;
        public int width_m;
        public int height_m;
    }


    public static String buildFlickrGeoSearchURL(String sortMethod) {
        return Uri.parse(FLICKR_API_BASE_URL).buildUpon()
                .appendQueryParameter(FLICKR_API_METHOD_PARAM, FLICKR_API_SEARCH_METHOD)
                .appendQueryParameter(FLICKR_API_KEY_PARAM, FLICKR_API_KEY)
                .appendQueryParameter(FLICKR_API_FORMAT_PARAM, FLICKR_API_FORMAT_JSON)
                .appendQueryParameter(FLICKR_API_NOJSONCALLBACK_PARAM, FLICKR_API_NOJSONCALLBACK)
                .appendQueryParameter(FLICKR_API_LAT_PARAM, Double.toString(currentLat))
                .appendQueryParameter(FLICKR_API_LON_PARAM, Double.toString(currentLon))
                .appendQueryParameter(FLICKR_API_RADIUS_PARAM, FLICKR_API_RADIUS)
                .appendQueryParameter(FLICKR_API_TAG_PARAM, FLICKR_API_TAG)
                .appendQueryParameter(FLICKR_API_MEDIA_PARAM, FLICKR_API_MEDIA_TYPE)
                .appendQueryParameter(FLICKR_API_SORT_PARAM, sortMethod)
                .appendQueryParameter(FLICKR_API_EXTRAS_PARAM, TextUtils.join(",", FLICKR_API_EXTRAS))
                .build()
                .toString();
    }

    public static FlickrPhoto[] parseFlickrExploreResultsJSON(String exploreResultsJSON) {
        FlickrExploreResults exploreResults = gson.fromJson(exploreResultsJSON, FlickrExploreResults.class);
        FlickrPhoto[] photos = null;
        if (exploreResults != null && exploreResults.photos != null) {
            photos = exploreResults.photos.photo;
        }
        return photos;
    }

}

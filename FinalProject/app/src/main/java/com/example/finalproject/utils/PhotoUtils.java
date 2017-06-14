package com.example.finalproject.utils;

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
    private static final String FLICKR_API_EXPLORE_METHOD = "flickr.interestingness.getList";
    private static final String FLICKR_API_KEY_PARAM = "api_key";
    private static final String FLICKR_API_FORMAT_PARAM = "format";
    private static final String FLICKR_API_FORMAT_JSON = "json";
    private static final String FLICKR_API_NOJSONCALLBACK_PARAM = "nojsoncallback";
    private static final String FLICKR_API_NOJSONCALLBACK = "1";
    private static final String FLICKR_API_EXTRAS_PARAM = "extras";
    private static final String[] FLICKR_API_EXTRAS = {"url_l", "url_m", "owner_name"};

    private static final String FLICKR_API_KEY = BuildConfig.FLICKR_API_KEY;

    private static final Gson gson = new Gson();

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
        public String url_l;
        public String url_m;
        public int width_l;
        public int height_l;
        public int width_m;
        public int height_m;
    }

    public static String buildFlickrExploreURL() {
        return Uri.parse(FLICKR_API_BASE_URL).buildUpon()
                .appendQueryParameter(FLICKR_API_METHOD_PARAM, FLICKR_API_EXPLORE_METHOD)
                .appendQueryParameter(FLICKR_API_KEY_PARAM, FLICKR_API_KEY)
                .appendQueryParameter(FLICKR_API_FORMAT_PARAM, FLICKR_API_FORMAT_JSON)
                .appendQueryParameter(FLICKR_API_NOJSONCALLBACK_PARAM, FLICKR_API_NOJSONCALLBACK)
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

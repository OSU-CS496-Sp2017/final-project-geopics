package com.example.finalproject;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.finalproject.utils.PhotoUtils;
import com.example.finalproject.utils.NetworkUtils;

import java.io.IOException;


public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<String>, FlickrPhotoGridAdapter.OnPhotoItemClickListener {

    private static final String TAG = "YOURTAG";
    private static final int FLICKR_EXPLORE_LOADER_ID = 0;
    private static final int NUM_PHOTO_COLUMNS = 3;

    private RecyclerView mPhotosRV;
    private ProgressBar mLoadingIndicatorPB;
    private TextView mLoadingErrorMessageTV;

    private PhotoUtils.FlickrPhoto[] mPhotos;
    private FlickrPhotoGridAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingIndicatorPB = (ProgressBar)findViewById(R.id.pb_loading_indicator);
        mLoadingErrorMessageTV = (TextView)findViewById(R.id.tv_loading_error_message);
        mPhotosRV = (RecyclerView)findViewById(R.id.rv_photos);

        mAdapter = new FlickrPhotoGridAdapter(this);
        mPhotosRV.setLayoutManager(new StaggeredGridLayoutManager(NUM_PHOTO_COLUMNS, StaggeredGridLayoutManager.VERTICAL));
        mPhotosRV.setHasFixedSize(true);
        mPhotosRV.setAdapter(mAdapter);

        getSupportLoaderManager().initLoader(FLICKR_EXPLORE_LOADER_ID, null, this);
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<String>(this) {

            String mExploreResultsJSON;

            @Override
            protected void onStartLoading() {
                if (mExploreResultsJSON != null) {
                    deliverResult(mExploreResultsJSON);
                } else {
                    mLoadingIndicatorPB.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }

            @Override
            public String loadInBackground() {
                String flickrExploreURL = PhotoUtils.buildFlickrExploreURL();
                String exploreResults = null;
                try {
                    exploreResults = NetworkUtils.doHTTPGet(flickrExploreURL);
                } catch (IOException e) {
                    Log.d(TAG, "Error connecting to Flickr", e);
                }
                return exploreResults;
            }

            @Override
            public void deliverResult(String data) {
                mExploreResultsJSON = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
        if (data != null) {
            mLoadingErrorMessageTV.setVisibility(View.INVISIBLE);
            mPhotosRV.setVisibility(View.VISIBLE);
            mPhotos = PhotoUtils.parseFlickrExploreResultsJSON(data);
            mAdapter.updatePhotos(mPhotos);
//            for (FlickrUtils.FlickrPhoto photo : photos) {
//                Log.d(TAG, "Got photo: " + photo.url_m);
//            }
        } else {
            mPhotosRV.setVisibility(View.INVISIBLE);
            mLoadingErrorMessageTV.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        // Nothing.
    }

    @Override
    public void onPhotoItemClick(int photoIdx) {
        Log.d(TAG, "Clicked photo: " + photoIdx);
//        Intent intent = new Intent(this, PhotoViewActivity.class);
//        intent.putExtra(PhotoViewActivity.EXTRA_PHOTOS, mPhotos);
//        intent.putExtra(PhotoViewActivity.EXTRA_PHOTO_IDX, photoIdx);
//        startActivity(intent);

        Intent intent = new Intent(this, PhotoDetailView.class);
        intent.putExtra(PhotoDetailView.EXTRA_PHOTOS, mPhotos);
        intent.putExtra(PhotoDetailView.EXTRA_PHOTO_IDX, photoIdx);
        startActivity(intent);
    }
}


package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.finalproject.utils.PhotoUtils;

/**
 * Created by Frog on 6/13/17.
 */

public class PhotoDetailView extends AppCompatActivity {

    public static final String EXTRA_PHOTOS = "photos";

    public static final String EXTRA_PHOTO_IDX = "photoIdx";


    private ImageView mPhotoIV;
    private TextView mTitleTV;
    private TextView mAuthorTV;
    private TextView mDateTV;
    private TextView mLatTV;
    private TextView mLonTV;
    private TextView mTagsTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mPhotoIV = (ImageView)findViewById(R.id.iv_photo_large);
        mTitleTV = (TextView)findViewById(R.id.tv_title);
        mAuthorTV = (TextView)findViewById(R.id.tv_author);
        mDateTV = (TextView)findViewById(R.id.tv_date_upload);
        mLatTV = (TextView)findViewById(R.id.tv_lat);
        mLonTV = (TextView)findViewById(R.id.tv_lon);
        mTagsTV = (TextView)findViewById(R.id.tv_tags);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_PHOTOS)) {
            PhotoUtils.FlickrPhoto[] photos = (PhotoUtils.FlickrPhoto[]) intent.getSerializableExtra(EXTRA_PHOTOS);
            String photoURL = photos[intent.getIntExtra(EXTRA_PHOTO_IDX, 0)].url_l;
            Glide.with(mPhotoIV.getContext())
                    .load(photoURL)
                    .into(mPhotoIV);

            String titleString = "Title: " + photos[intent.getIntExtra(EXTRA_PHOTO_IDX, 0)].title;
            mTitleTV.setText(titleString);
            String authorString = "Author: " + photos[intent.getIntExtra(EXTRA_PHOTO_IDX, 0)].ownername;
            mAuthorTV.setText(authorString);
            String dateString = "Date: " + photos[intent.getIntExtra(EXTRA_PHOTO_IDX, 0)].date_upload;
            mDateTV.setText(dateString);
            String latString = "Latitude: " + photos[intent.getIntExtra(EXTRA_PHOTO_IDX, 0)].latitude;
            mLatTV.setText(latString);
            String lonString = "Longitude: " + photos[intent.getIntExtra(EXTRA_PHOTO_IDX, 0)].longitude;
            mLonTV.setText(lonString);
            String tagsString = "Tags: " + photos[intent.getIntExtra(EXTRA_PHOTO_IDX, 0)].tags;
            mTagsTV.setText(tagsString);


        }

    }





}

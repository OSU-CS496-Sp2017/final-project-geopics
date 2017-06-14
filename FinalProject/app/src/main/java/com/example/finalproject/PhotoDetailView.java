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

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_PHOTOS)) {
            PhotoUtils.FlickrPhoto[] photos = (PhotoUtils.FlickrPhoto[]) intent.getSerializableExtra(EXTRA_PHOTOS);
            String photoURL = photos[intent.getIntExtra(EXTRA_PHOTO_IDX, 0)].url_l;
            Glide.with(mPhotoIV.getContext())
                    .load(photoURL)
                    .into(mPhotoIV);
            fillLayoutText();
        }

    }


    private void fillLayoutText(){

        String titleString;
        String ownerString;


    }




}

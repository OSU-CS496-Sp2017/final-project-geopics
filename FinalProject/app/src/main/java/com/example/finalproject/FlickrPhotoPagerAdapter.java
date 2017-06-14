package com.example.finalproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.finalproject.utils.PhotoUtils;

/**
 * Created by hessro on 6/8/17.
 */

public class FlickrPhotoPagerAdapter extends FragmentStatePagerAdapter {

    PhotoUtils.FlickrPhoto[] mPhotos;

    public FlickrPhotoPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void updatePhotos(PhotoUtils.FlickrPhoto[] photos) {
        mPhotos = photos;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new FlickrPhotoFragment();
        Bundle args = new Bundle();
        args.putString(FlickrPhotoFragment.ARGS_PHOTO_URL, mPhotos[position].url_l);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        if (mPhotos != null) {
            return mPhotos.length;
        } else {
            return 0;
        }
    }

    public static class FlickrPhotoFragment extends Fragment {

        public static final String ARGS_PHOTO_URL = "photoUrl";

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.photo_pager_item, container, false);
            Bundle args = getArguments();
            ImageView photoIV = (ImageView)rootView.findViewById(R.id.iv_photo);
            String photoUrl = args.getString(ARGS_PHOTO_URL);
            Glide.with(photoIV.getContext())
                    .load(photoUrl)
                    .into(photoIV);
            return rootView;
        }
    }
}

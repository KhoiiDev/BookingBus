package com.example.myappcoach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SlideShowHomeAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<Image_SlideShow> list;

    public SlideShowHomeAdapter(Context context, ArrayList<Image_SlideShow> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_slideshow, container, false);

        ImageView imageView = view.findViewById(R.id.imgPlace);

        Image_SlideShow image_slideShow = list.get(position);
        if(image_slideShow != null){
            Glide.with(context).load(image_slideShow.getResourceID()).into(imageView);
        }

        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

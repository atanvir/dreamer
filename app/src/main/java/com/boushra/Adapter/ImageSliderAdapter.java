package com.boushra.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.boushra.Model.Data;
import com.boushra.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class ImageSliderAdapter extends PagerAdapter {
    private Context context;
    private  List<Data> data;

    public ImageSliderAdapter(Context context, List<Data> data)
    {
        this.data=data;
        this.context=context;

    }


    private int[] sliderImageId = new int[]{};


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
       LayoutInflater  inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       View view = inflater.inflate(R.layout.slider_image,null);
       ImageView icon_iv=view.findViewById(R.id.icon_iv);
       TextView titletxt=view.findViewById(R.id.titletxt);
       TextView desc_txt=view.findViewById(R.id.desc_txt);
       Glide.with(context).load(data.get(position).getImage()).into(icon_iv);
       titletxt.setText(data.get(position).getTitle());
       desc_txt.setText(data.get(position).getDescription());

        ((ViewPager) container).addView(view, position);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((View) object);

    }
}

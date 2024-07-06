package com.furkanekiz.streetpals.home.slider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.furkanekiz.streetpals.R;
import com.furkanekiz.streetpals.home.slider.model.Card;
import com.smarteist.autoimageslider.SliderViewAdapter;


public class SliderAdapter extends SliderViewAdapter<SliderAdapter.Holder> {

    Card[] images;

    public SliderAdapter(Card[] images) {

        this.images = images;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_slider, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, int position) {

        Card imageList = images[position];

        viewHolder.imageView.setImageResource(imageList.getImage());

    }

    @Override
    public int getCount() {
        return images.length;
    }

    public static class Holder extends ViewHolder {

        ImageView imageView;

        public Holder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);

        }
    }

}
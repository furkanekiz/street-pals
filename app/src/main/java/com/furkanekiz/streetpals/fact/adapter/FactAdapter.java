package com.furkanekiz.streetpals.fact.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.furkanekiz.streetpals.R;
import com.furkanekiz.streetpals.fact.model.FactCard;

import java.util.List;

public class FactAdapter extends RecyclerView.Adapter<FactAdapter.ViewHolder> {

    List<FactCard> itemList;
    int itemLayoutId;
    Context context;
    public ImageView imgDot;

    public FactAdapter(List<FactCard> itemList, int itemLayoutId, Context context) {
        this.context = context;
        this.itemList = itemList;
        this.itemLayoutId = itemLayoutId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FactCard animalCard = itemList.get(position);
        holder.bindData(animalCard);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtInfo = itemView.findViewById(R.id.txtInfo);
            imgDot = itemView.findViewById(R.id.imgDot);
        }

        @SuppressLint("DiscouragedApi")
        public void bindData(final FactCard factCard) {
            txtInfo.setText(factCard.getInfo());

            switch (factCard.getType()) {
                case "DOG":
                    loadImage("ic_icon_dot_dog");

                    break;
                case "CAT":
                    loadImage("ic_icon_dot_cat");

                    break;
                case "BIRD":
                    loadImage("ic_icon_dot_bird");

                    break;
            }

        }
    }

    @SuppressLint("DiscouragedApi")
    private void loadImage(String imageLocal) {

        int drawableId = 0;
        try {
            drawableId = context.getResources().getIdentifier(imageLocal, "drawable", context.getPackageName());
        } catch (Exception e) {
            Log.d("error:", "" + e.getMessage());
        }
        Glide.with(context).load(drawableId).into(imgDot);
    }
}
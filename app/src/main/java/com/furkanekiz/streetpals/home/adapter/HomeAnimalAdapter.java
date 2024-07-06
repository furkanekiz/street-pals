package com.furkanekiz.streetpals.home.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.furkanekiz.streetpals.R;
import com.furkanekiz.streetpals.databinding.CustomHomeAnimalAlertDialogBinding;
import com.furkanekiz.streetpals.home.model.AnimalCard;

import java.util.List;
import java.util.Objects;

public class HomeAnimalAdapter extends RecyclerView.Adapter<HomeAnimalAdapter.ViewHolder> {

    List<AnimalCard> itemList;
    int itemLayoutId;
    Context context;

    public HomeAnimalAdapter(List<AnimalCard> itemList, int itemLayoutId, Context context) {
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
        AnimalCard animalCard = itemList.get(position);
        holder.bindData(animalCard);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgAnimal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnimal = itemView.findViewById(R.id.imgAnimal);
        }

        @SuppressLint("DiscouragedApi")
        public void bindData(final AnimalCard animalCard) {
            String imageLocal = animalCard.getLocalImageResource();

            int drawableId = 0;
            try {
                drawableId = context.getResources().getIdentifier(imageLocal, "drawable", context.getPackageName());
            } catch (Exception e) {
                Log.d("error:", "" + e.getMessage());
            }

            Glide.with(context).load(drawableId).into(imgAnimal);

            imgAnimal.setOnClickListener(view -> showCustomDialog(context, animalCard));
        }
    }

    public void showCustomDialog(Context context, AnimalCard card) {
        CustomHomeAnimalAlertDialogBinding binding = CustomHomeAnimalAlertDialogBinding.inflate(LayoutInflater.from(context));

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(binding.getRoot());

        binding.txtTitle.setText(card.getTitle());
        binding.txtDesc.setText(card.getDescription());
        binding.txtExtraDesc.setText(card.getExtraText());
        alertDialogBuilder.setCancelable(false);

        final AlertDialog alertDialog = alertDialogBuilder.create();

        binding.imgExit.setOnClickListener(view -> alertDialog.dismiss());

        alertDialog.show();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(R.drawable.alert_background);

    }

}
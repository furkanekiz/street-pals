
package com.furkanekiz.streetpals.home.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.furkanekiz.streetpals.databinding.CustomHomeOrganizationAlertDialogBinding;
import com.furkanekiz.streetpals.home.model.OrganizationCard;

import java.util.List;
import java.util.Objects;


public class HomeOrganizationAdapter extends RecyclerView.Adapter<HomeOrganizationAdapter.ViewHolder> {

    List<OrganizationCard> itemList;
    int itemLayoutId;
    Context context;

    public HomeOrganizationAdapter(List<OrganizationCard> itemList, int itemLayoutId, Context context) {
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
        OrganizationCard item = itemList.get(position);
        holder.bindData(item);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgOrganization;
        TextView txtOrganization;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgOrganization = itemView.findViewById(R.id.imgOrganization);
            txtOrganization = itemView.findViewById(R.id.txtOrganization);
            txtOrganization.setSelected(true);
        }

        @SuppressLint("DiscouragedApi")
        public void bindData(final OrganizationCard item) {
            String imageLocal = item.getPoster();

            int drawableId = 0;
            try {
                drawableId = context.getResources().getIdentifier(imageLocal, "drawable", context.getPackageName());
            } catch (Exception e) {
                Log.d("error:",""+e.getMessage());
            }
            Glide.with(context).load(drawableId)
                    .fitCenter()
                    .into(imgOrganization);
            txtOrganization.setText(item.getTitle());

            itemView.setOnClickListener(view -> showCustomDialog(context,item));

        }
    }


        public void showCustomDialog(Context context,OrganizationCard organizationCard) {
            CustomHomeOrganizationAlertDialogBinding binding = CustomHomeOrganizationAlertDialogBinding.inflate(LayoutInflater.from(context));

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setView(binding.getRoot());

            binding.txtTitle.setText(organizationCard.getTitle());
            binding.txtDesc.setText(organizationCard.getDesc());
            String director= context.getString(R.string.string_app_director)+organizationCard.getDirector();
            binding.txtDirector.setText(director);
            String year= context.getString(R.string.string_app_year)+organizationCard.getYear();
            binding.txtYear.setText(year);
            alertDialogBuilder.setCancelable(false);

            final AlertDialog alertDialog = alertDialogBuilder.create();

            binding.buttonOK.setOnClickListener(v -> {
                alertDialog.dismiss();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(organizationCard.getUrl()));
                context.startActivity(browserIntent);
            });
            binding.imgExit.setOnClickListener(view -> alertDialog.dismiss());

            alertDialog.show();
            Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(R.drawable.alert_background);

        }


}
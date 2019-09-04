package com.example.placesapiclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PlacesAAdapter extends RecyclerView.Adapter<PlacesAAdapter.MyViewHolder> {
    private List<PlaceItem> placeItems = new ArrayList<>();
    private Context context;
    private ClickMaanger clickMaanger;

    public PlacesAAdapter(Context context,ClickMaanger clickMaanger) {
        this.context = context;
        this.clickMaanger=clickMaanger;
    }

    public interface ClickMaanger{
        public void onItemClick(int positon);
    }

    public void setList(List<PlaceItem> placeItemList) {
        if (placeItemList == null)
            return;
        placeItems.clear();
        placeItems.addAll(placeItemList);
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_place, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvPrimary.setText(placeItems.get(position).getPrimaryTxt());
        holder.tvSecondary.setText(placeItems.get(position).getSecondaryTxt());
    }

    @Override
    public int getItemCount() {
        return placeItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPrimary, tvSecondary;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPrimary = (TextView) itemView.findViewById(R.id.primarytxt);
            tvSecondary = (TextView) itemView.findViewById(R.id.secondaryTxt);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickMaanger.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}

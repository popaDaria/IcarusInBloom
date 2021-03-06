package com.sunny.icarusinbloom.recycler_elem;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.sunny.icarusinbloom.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PlantItemAdapter extends RecyclerView.Adapter<PlantItemAdapter.ViewHolder> {

    private List<PlantItem> plants;
    OnListItemClick listItemClick;

    public PlantItemAdapter(List<PlantItem> plants, OnListItemClick listItemClick) {
        this.plants = plants;
        this.listItemClick = listItemClick;
    }

    public void setPlants(List<PlantItem> plants) {
        this.plants.clear();
        this.plants.addAll(plants);
    }

    @NonNull
    @Override
    public PlantItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.plant_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantItemAdapter.ViewHolder holder, int position) {
        holder.name.setText(plants.get(position).getName());
        if(!plants.get(position).getInfo().equals("null")) {
            holder.info.setText(plants.get(position).getInfo());
        }
        if(plants.get(position).getImage().equals("null")) {
            holder.photo.setImageResource(R.drawable.plant);
        }
        else {
            Picasso.get().load(Uri.parse(plants.get(position).getImage())).centerCrop().fit().into(holder.photo);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
        Date today = new Date();
        if(sdf.format(today).equals(plants.get(position).getBday().substring(0,5))){
            holder.bdayImg.setVisibility(View.VISIBLE);
        }else{
            holder.bdayImg.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        if(plants!=null)
            return plants.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView info;
        ImageView photo;
        ImageView bdayImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.plantImage);
            name = itemView.findViewById(R.id.plantName);
            info = itemView.findViewById(R.id.plantInfo);
            bdayImg = itemView.findViewById(R.id.plantBdayImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listItemClick.onClick(plants.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnListItemClick{
        void onClick(PlantItem plantItem);
    }
}

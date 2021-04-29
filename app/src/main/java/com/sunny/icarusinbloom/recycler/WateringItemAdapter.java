package com.sunny.icarusinbloom.recycler;

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
import com.sunny.icarusinbloom.webservice.SpeciesInfo;

import java.util.List;

public class WateringItemAdapter extends RecyclerView.Adapter<WateringItemAdapter.ViewHolder> {
    private List<PlantItem> plants;
    private List<SpeciesInfo> species;
    OnListItemClick listItemClick;

    public WateringItemAdapter(List<PlantItem> plants, OnListItemClick onListItemClick, List<SpeciesInfo> species) {
        this.plants = plants;
        this.listItemClick=onListItemClick;
        this.species=species;
    }

    public void setPlants(List<PlantItem> plants) {
        this.plants.clear();
        this.plants.addAll(plants);
    }

    public void setSpecies(List<SpeciesInfo> species) {
        this.species.clear();
        this.species.addAll(species);
    }

    @NonNull
    @Override
    public WateringItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.watering_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WateringItemAdapter.ViewHolder holder, int position) {
        holder.name.setText(plants.get(position).getName()+"'s water needs");
        if(plants.get(position).getImage().equals("null")) {
            holder.plantPhoto.setImageResource(R.drawable.plant);
        }
        else {
            Picasso.get().load(Uri.parse(plants.get(position).getImage())).centerCrop().fit().into(holder.plantPhoto);
        }
        holder.wateringInfo.setText(plants.get(position).getWater_type()+", every "+plants.get(position).getWater_interval()+" days");
        holder.lastWaterDate.setText("Last watered: "+plants.get(position).getLastWatered());

        SpeciesInfo specie = null;
        for (SpeciesInfo sp:species) {
            if(plants.get(position).getSpeciesId()==sp.getSpeciesId()){
                specie=sp;
            }
        }
        if(specie!=null){
            if(specie.getSoilLevel()>=5)
                holder.soilChange.setText("Soil change: 2/year");
            else
                holder.soilChange.setText("Soil change: 1/year");
        }else{
            holder.soilChange.setText("Soil change: no indications");
        }
    }

    @Override
    public int getItemCount() {
        if(plants!=null)
            return plants.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView wateringInfo;
        TextView lastWaterDate;
        TextView soilChange;
        ImageView plantPhoto;
        ImageView waterButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.plantNameWatering);
            wateringInfo = itemView.findViewById(R.id.wateringInfoField);
            lastWaterDate = itemView.findViewById(R.id.lastWateredDate);
            plantPhoto = itemView.findViewById(R.id.wateringPlantPhoto);
            waterButton = itemView.findViewById(R.id.waterPlantButton);
            soilChange = itemView.findViewById(R.id.soilChange);

            waterButton.setOnClickListener(new View.OnClickListener() {
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

package com.sunny.icarusinbloom.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.sunny.icarusinbloom.MainActivity;
import com.sunny.icarusinbloom.R;
import com.sunny.icarusinbloom.recycler_elem.PlantItem;
import com.sunny.icarusinbloom.recycler_elem.PlantItemViewModel;
import com.sunny.icarusinbloom.recycler_elem.SpeciesViewModel;
import com.sunny.icarusinbloom.recycler_elem.WateringItemAdapter;
import com.sunny.icarusinbloom.webservice.SpeciesInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Fragment2 extends Fragment implements WateringItemAdapter.OnListItemClick {

    PlantItemViewModel viewModel;
    List<PlantItem> list = new ArrayList<>();
    SpeciesViewModel speciesViewModel;
    List<SpeciesInfo> speciesInfos = new ArrayList<>();

    WateringItemAdapter adapter;
    View rootView;
    RecyclerView recyclerView;

    ImageView noPlantImg;
    TextView noPlantText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment2,container,false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewModel = new ViewModelProvider(this).get(PlantItemViewModel.class);
        speciesViewModel = new ViewModelProvider(this).get(SpeciesViewModel.class);

        noPlantImg = rootView.findViewById(R.id.noPlantsImage);
        noPlantText = rootView.findViewById(R.id.noPlantsText);
        setNoPlantViewOff();

        FloatingActionButton fab = rootView.findViewById(R.id.fab_water);
        fab.setOnClickListener(v -> {
            waterAll();
        });

        viewModel.getAllPlantsByWatered(MainActivity.loggedUser.getId()).observe(getViewLifecycleOwner(), plants->{
            if(plants!=null){
                list=plants;
                adapter.setPlants(list);
                adapter.notifyDataSetChanged();
                setNoPlantViewOff();
                fab.setVisibility(View.VISIBLE);
                if(plants.size()==0) {
                    setNoPlantViewOn();
                    fab.setVisibility(View.INVISIBLE);
                }
            }else{
                setNoPlantViewOn();
                fab.setVisibility(View.INVISIBLE);
            }
        });

        speciesViewModel.getAllSpecies().observe(getViewLifecycleOwner(), species->{
            if(species!=null){
                speciesInfos = species;
                adapter.setSpecies(speciesInfos);
                adapter.notifyDataSetChanged();
            }
        });

        adapter = new WateringItemAdapter(list,this,speciesInfos);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }

    private void setNoPlantViewOn(){
        noPlantImg.setVisibility(View.VISIBLE);
        noPlantText.setVisibility(View.VISIBLE);
    }
    private void setNoPlantViewOff(){
        noPlantImg.setVisibility(View.INVISIBLE);
        noPlantText.setVisibility(View.INVISIBLE);
    }
    private void waterAll() {

        Snackbar snackbar = Snackbar.make(rootView,"Do you want to water all?", Snackbar.LENGTH_LONG)
                .setAction("CONFIRM", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date today = new Date();
                        String newWateredDate = sdf.format(today);
                        for (PlantItem plant:list) {
                            plant.setLastWatered(newWateredDate);
                            viewModel.update(plant);
                        }
                        updateList();
                        Toast.makeText(getContext(),"All plants marked as watered!",Toast.LENGTH_SHORT).show();

                    }
                });
        snackbar.show();
    }

    private void updateList(){
        viewModel.getAllPlantsByWatered(MainActivity.loggedUser.getId()).observe(getViewLifecycleOwner(),plants->{
            if(plants!=null){
                list=plants;
                adapter.setPlants(list);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(PlantItem plantItem) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        String newWateredDate = sdf.format(today);

        if(newWateredDate.equals(plantItem.getLastWatered())){
            Toast.makeText(getContext(),"You've watered "+plantItem.getName()+" today already!",Toast.LENGTH_SHORT).show();
        }else {
            plantItem.setLastWatered(newWateredDate);
            viewModel.update(plantItem);
            updateList();
            Toast.makeText(getContext(), "You've watered " + plantItem.getName() + "! c:", Toast.LENGTH_SHORT).show();
        }
    }
}

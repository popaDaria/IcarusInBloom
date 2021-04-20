package com.sunny.icarusinbloom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.sunny.icarusinbloom.recycler.PlantItem;
import com.sunny.icarusinbloom.recycler.PlantItemAdapter;
import com.sunny.icarusinbloom.recycler.PlantItemViewModel;
import com.sunny.icarusinbloom.recycler.SpeciesViewModel;
import com.sunny.icarusinbloom.recycler.WateringItemAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Fragment2 extends Fragment implements WateringItemAdapter.OnListItemClick {

    PlantItemViewModel viewModel;
    List<PlantItem> list = new ArrayList<>();
    WateringItemAdapter adapter;
    View rootView;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment2,container,false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewModel = new ViewModelProvider(this).get(PlantItemViewModel.class);

        viewModel.getAllPlantsByWatered(MainActivity.loggedUser.getId()).observe(getViewLifecycleOwner(),plants->{
            if(plants!=null){
               /* for (PlantItem p:plants) {
                    System.out.println(p.toString());
                }*/
                list=plants;
                adapter.setPlants(list);
                adapter.notifyDataSetChanged();
            }
        });

        adapter = new WateringItemAdapter(list,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        FloatingActionButton fab = rootView.findViewById(R.id.fab_water);
        fab.setOnClickListener(v -> {
            waterAll();
        });

        return rootView;
    }

    private void waterAll() {
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

    private void updateList(){
        viewModel.getAllPlantsByWatered(MainActivity.loggedUser.getId()).observe(getViewLifecycleOwner(),plants->{
            if(plants!=null){
                for (PlantItem p:plants) {
                    System.out.println(p.toString());
                }
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

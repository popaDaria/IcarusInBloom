package com.sunny.icarusinbloom.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.sunny.icarusinbloom.AddActivity;
import com.sunny.icarusinbloom.MainActivity;
import com.sunny.icarusinbloom.PlantInfoActivity;
import com.sunny.icarusinbloom.R;
import com.sunny.icarusinbloom.recycler_elem.PlantItem;
import com.sunny.icarusinbloom.recycler_elem.PlantItemAdapter;
import com.sunny.icarusinbloom.recycler_elem.PlantItemViewModel;
import com.sunny.icarusinbloom.recycler_elem.SpeciesViewModel;
import com.sunny.icarusinbloom.webservice.SpeciesInfo;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class Fragment1 extends Fragment implements PlantItemAdapter.OnListItemClick{

    PlantItemViewModel viewModel;
    SpeciesViewModel speciesViewModel;
    List<PlantItem> list = new ArrayList<>();
    PlantItemAdapter adapter;
    View rootView;
    RecyclerView recyclerView;
    ImageView noPlantImg;
    TextView noPlantText;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment1,container,false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewModel = new ViewModelProvider(this).get(PlantItemViewModel.class);
        speciesViewModel = new ViewModelProvider(this).get(SpeciesViewModel.class);

        noPlantImg = rootView.findViewById(R.id.noPlantsImage);
        noPlantText = rootView.findViewById(R.id.noPlantsText);
        setNoPlantViewOff();

        viewModel.getAllUserPlant(MainActivity.loggedUser.getId()).observe(getViewLifecycleOwner(), plants->{
            if(plants!=null){
                list=plants;
                adapter.setPlants(list);
                adapter.notifyDataSetChanged();
                if(plants.size()==0){
                    setNoPlantViewOn();
                }
            }else{
                setNoPlantViewOn();
            }

        });

        adapter = new PlantItemAdapter(list,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        FloatingActionButton fab = rootView.findViewById(R.id.fab_add);
        fab.setOnClickListener(v -> {
            presentActivity(v);
        });

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

    public void presentActivity(View view){
        int revealX = (int) (view.getX() + view.getWidth() /2);
        int revealY = (int) (view.getY() + view.getHeight() /2);

        Intent intent = new Intent(getContext(), AddActivity.class);
        intent.putExtra(AddActivity.EXTRA_CIRCULAR_REVEAL_X,revealX);
        intent.putExtra(AddActivity.EXTRA_CIRCULAR_REVEAL_Y,revealY);
        intent.putExtra("loggedUser",MainActivity.loggedUser);

        startActivityForResult(intent,1);
    }

    boolean found=false;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode==RESULT_OK){
                super.onActivityResult(requestCode, resultCode, data);
                Bundle bundle = data.getExtras();
                PlantItem added = (PlantItem) bundle.getSerializable("plantAdded");
                SpeciesInfo speciesAdded = (SpeciesInfo) bundle.getSerializable("speciesAdded");
                viewModel.addPlant(added);
                if(speciesAdded!=null){
                    found=false;
                    speciesViewModel.getAllSpecies().observe(getViewLifecycleOwner(),species->{
                        if(species!=null){
                            for(SpeciesInfo si : species){
                                if(si.getSpeciesId()==speciesAdded.getSpeciesId()) {
                                    found = true;
                                }
                            }
                        }
                    });
                    if(!found) {
                        speciesViewModel.insert(speciesAdded);
                    }else{
                        System.out.println("already existing species info in table");
                    }
                }
            }
        }else if(requestCode==3){
            if(resultCode==RESULT_OK){
                super.onActivityResult(requestCode,resultCode,data);
                Snackbar snackbar = Snackbar.make(rootView, "Are you sure you want to delete the plant?",Snackbar.LENGTH_LONG)
                        .setAction("Delete", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bundle bundle = data.getExtras();
                                PlantItem toDelete = (PlantItem) bundle.getSerializable("deletePlant");
                                viewModel.deletePlant(toDelete);
                            }
                        });
                snackbar.show();
            }
        }
        viewModel.getAllUserPlant(MainActivity.loggedUser.getId()).observe(this,plants->{
            if(plants != null){
                list=plants;
                adapter.setPlants(list);
                adapter.notifyDataSetChanged();
                setNoPlantViewOff();
                if(plants.size()==0){
                    setNoPlantViewOn();
                }
            }else{
                setNoPlantViewOn();
            }
        });
    }

    SpeciesInfo speciesInfo;
    @Override
    public void onClick(PlantItem plantItem) {
        Intent intent = new Intent(getContext(), PlantInfoActivity.class);
        intent.putExtra("plant", plantItem);
        if(plantItem.getSpeciesId()!=-1) {
            speciesViewModel.getSpeciesById(plantItem.getSpeciesId()).observe(getViewLifecycleOwner(), info->{
                if(info!=null){
                    speciesInfo=info;
                    intent.putExtra("species",speciesInfo);
                    startActivityForResult(intent,3);
                }else{
                    startActivityForResult(intent,3);
                }
            });
        }else{
            startActivityForResult(intent,3);
        }
    }

}

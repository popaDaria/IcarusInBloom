package com.sunny.icarusinbloom;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.sunny.icarusinbloom.login.User;
import com.sunny.icarusinbloom.login.UserViewModel;
import com.sunny.icarusinbloom.recycler.PlantItem;
import com.sunny.icarusinbloom.recycler.PlantItemAdapter;
import com.sunny.icarusinbloom.recycler.PlantItemViewModel;
import com.sunny.icarusinbloom.recycler.SpeciesViewModel;
import com.sunny.icarusinbloom.webservice.SpeciesInfo;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class Fragment1 extends Fragment implements PlantItemAdapter.OnListItemClick{

    PlantItemViewModel viewModel;
    SpeciesViewModel speciesViewModel;
    //UserViewModel viewModel1;
    List<PlantItem> list = new ArrayList<>();
    PlantItemAdapter adapter;
    View rootView;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment1,container,false);

      /*  int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        rootView.setSystemUiVisibility(uiOptions);*/

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewModel = new ViewModelProvider(this).get(PlantItemViewModel.class);
        speciesViewModel = new ViewModelProvider(this).get(SpeciesViewModel.class);

        viewModel.getAllUserPlant(MainActivity.loggedUser.getId()).observe(getViewLifecycleOwner(),plants->{
            if(plants!=null){
                for (PlantItem p:plants) {
                    //System.out.println(p.toString());
                }
                list=plants;
                adapter.setPlants(list);
                adapter.notifyDataSetChanged();
            }
        });

        adapter = new PlantItemAdapter(list,this);

        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        FloatingActionButton fab = rootView.findViewById(R.id.fab_add);
        fab.setOnClickListener(v -> {
            /*Intent intent = new Intent(getContext(), AddActivity.class);
            startActivityForResult(intent, 1);*/
            presentActivity(v);
        });

        return rootView;
    }

    public void presentActivity(View view){
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),view,"transition");
        int revealX = (int) (view.getX() + view.getWidth() /2);
        int revealY = (int) (view.getY() + view.getHeight() /2);

        Intent intent = new Intent(getContext(), AddActivity.class);
        intent.putExtra(AddActivity.EXTRA_CIRCULAR_REVEAL_X,revealX);
        intent.putExtra(AddActivity.EXTRA_CIRCULAR_REVEAL_Y,revealY);
        intent.putExtra("loggedUser",MainActivity.loggedUser);

        //ActivityCompat.startActivity(getContext(),intent,options.toBundle());
        //ActivityCompat.startActivityForResult(getActivity(),intent,1,options.toBundle());
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
                        Log.i("inserting", "inserting species info into table");
                    }else{
                        Log.i("inserting", "already existing species info in table");
                    }
                }
                /*Toast toast = Toast.makeText(getContext(),"info received:"+ added.toString(),Toast.LENGTH_LONG);
                toast.show();*/
                //adapter.notifyDataSetChanged();
            }
        }else if(requestCode==3){
            if(resultCode==RESULT_OK){
                super.onActivityResult(requestCode,resultCode,data);
                Bundle bundle = data.getExtras();
                PlantItem toDelete = (PlantItem) bundle.getSerializable("deletePlant");
                viewModel.deletePlant(toDelete);
            }
        }
        viewModel.getAllUserPlant(MainActivity.loggedUser.getId()).observe(this,plants->{
            if(plants != null){
               /* for (PlantItem p:plants) {
                    System.out.println("HIIII "+p.toString());
                }*/
                list=plants;
                adapter.setPlants(list);
                adapter.notifyDataSetChanged();
            }
        });
    }

    SpeciesInfo speciesInfo;
    @Override
    public void onClick(PlantItem plantItem) {
/*        Toast toast = Toast.makeText(getActivity(),"hi "+plantItem.toString(),Toast.LENGTH_SHORT);
        toast.show();*/
        Intent intent = new Intent(getContext(), PlantInfoActivity.class);
        intent.putExtra("plant", plantItem);
        if(plantItem.getSpeciesId()!=-1) {
            speciesViewModel.getSpeciesById(plantItem.getSpeciesId()).observe(getViewLifecycleOwner(), info->{
                if(info!=null){
                    speciesInfo=info;
                    intent.putExtra("species",speciesInfo);
                    //System.out.println("start from here!!!!!!!!!!!!!! "+speciesInfo.toString());
                    startActivityForResult(intent,3);
                }
            });
        }else{
           // System.out.println("start from hereee");
            startActivityForResult(intent,3);
        }
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

}

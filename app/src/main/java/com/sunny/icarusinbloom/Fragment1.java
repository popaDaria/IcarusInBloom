package com.sunny.icarusinbloom;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class Fragment1 extends Fragment implements PlantItemAdapter.OnListItemClick{

    PlantItemViewModel viewModel;
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


        //viewModel1 = new ViewModelProvider(this).get(UserViewModel.class);

        viewModel.getPlantList().observe(getViewLifecycleOwner(),plants->{
            if(plants!=null){
                list=plants;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode==RESULT_OK){

                super.onActivityResult(requestCode, resultCode, data);
                Bundle bundle = data.getExtras();
                PlantItem added = (PlantItem) bundle.getSerializable("plantAdded");
                //System.out.println("GOT TO THE FRAGMENT");
                viewModel.addPlant(added);
                /*Toast toast = Toast.makeText(getContext(),"info received:"+ added.toString(),Toast.LENGTH_LONG);
                toast.show();*/
                //adapter.notifyDataSetChanged();
            }
        }
        viewModel.getPlantList().observe(this,plants->{
            if(plants != null){
                for (PlantItem p:plants) {
                    System.out.println("HIIII "+p.toString());
                }
                list=plants;
                //adapter.setPlants(list);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(int position) {
        Toast toast = Toast.makeText(getActivity(),"hi "+position,Toast.LENGTH_SHORT);
        toast.show();
    }

}
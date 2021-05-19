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
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sunny.icarusinbloom.AddDiaryEntryActivity;
import com.sunny.icarusinbloom.MainActivity;
import com.sunny.icarusinbloom.R;
import com.sunny.icarusinbloom.recycler_elem.DiaryItem;
import com.sunny.icarusinbloom.recycler_elem.DiaryItemAdapter;
import com.sunny.icarusinbloom.recycler_elem.DiaryItemViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class Fragment3 extends Fragment implements DiaryItemAdapter.OnListItemClick2 {

    View rootView;
    RecyclerView recyclerView;
    DiaryItemViewModel viewModel;
    List<DiaryItem> list = new ArrayList<>();
    DiaryItemAdapter adapter;

    ImageView noDiary;
    TextView noDiaryText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment3,container,false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewDiary);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewModel = new ViewModelProvider(this).get(DiaryItemViewModel.class);

        noDiary = rootView.findViewById(R.id.noDiaryImage);
        noDiaryText = rootView.findViewById(R.id.noDiaryText);

        updateList();
        adapter = new DiaryItemAdapter(list,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        FloatingActionButton fab = rootView.findViewById(R.id.fab_add_diary);
        fab.setOnClickListener(v -> {
            presentActivity(v);
        });

        return rootView;
    }

    public void presentActivity(View view){
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),view,"transition");
        int revealX = (int) (view.getX() + view.getWidth() /2);
        int revealY = (int) (view.getY() + view.getHeight() /2);

        Intent intent = new Intent(getContext(), AddDiaryEntryActivity.class);
        intent.putExtra(AddDiaryEntryActivity.EXTRA_CIRCULAR_REVEAL_X,revealX);
        intent.putExtra(AddDiaryEntryActivity.EXTRA_CIRCULAR_REVEAL_Y,revealY);
        intent.putExtra("loggedUser", MainActivity.loggedUser);

        //ActivityCompat.startActivityForResult(getActivity(),intent,12,options.toBundle());
        startActivityForResult(intent,12);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==12){
            if(resultCode==RESULT_OK){
                Bundle bundle = data.getExtras();
                DiaryItem diaryItem = (DiaryItem) bundle.getSerializable("entryAdded");
                viewModel.insert(diaryItem);
                updateList();
            }
        }
    }

    private void updateList(){
        viewModel.getAllDiaryEntriesForUSer(MainActivity.loggedUser.getId()).observe(getViewLifecycleOwner(),entries ->{
            if(entries!=null){
                list=entries;
                adapter.setPlants(list);
                adapter.notifyDataSetChanged();
                if(entries.size()==0){
                    noDiary.setVisibility(View.VISIBLE);
                    noDiaryText.setVisibility(View.VISIBLE);
                }else{
                    noDiary.setVisibility(View.INVISIBLE);
                    noDiaryText.setVisibility(View.INVISIBLE);
                }
            }else{
                noDiary.setVisibility(View.VISIBLE);
                noDiaryText.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onClick(DiaryItem diaryItem) {
        viewModel.delete(diaryItem);
        updateList();
    }
}

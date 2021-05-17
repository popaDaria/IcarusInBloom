package com.sunny.icarusinbloom;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.SessionConfiguration;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.otaliastudios.cameraview.BitmapCallback;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.FileCallback;
import com.otaliastudios.cameraview.PictureResult;
import com.squareup.picasso.Picasso;
import com.sunny.icarusinbloom.diary.DiaryItem;
import com.sunny.icarusinbloom.diary.DiaryItemAdapter;
import com.sunny.icarusinbloom.diary.DiaryItemViewModel;
import com.sunny.icarusinbloom.model.CapturePhotoUtils;
import com.sunny.icarusinbloom.recycler.PlantItem;
import com.sunny.icarusinbloom.recycler.PlantItemAdapter;
import com.sunny.icarusinbloom.recycler.PlantItemViewModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

//SurfaceHolder.Callback, Handler.Callback
public class Fragment3 extends Fragment implements DiaryItemAdapter.OnListItemClick2 {

    View rootView;
    RecyclerView recyclerView;
    DiaryItemViewModel viewModel;
    List<DiaryItem> list = new ArrayList<>();
    DiaryItemAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment3,container,false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewDiary);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewModel = new ViewModelProvider(this).get(DiaryItemViewModel.class);

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
        int revealX = (int) (view.getX() + view.getWidth() /2);
        int revealY = (int) (view.getY() + view.getHeight() /2);

        Intent intent = new Intent(getContext(), AddDiaryEntry.class);
        intent.putExtra(AddDiaryEntry.EXTRA_CIRCULAR_REVEAL_X,revealX);
        intent.putExtra(AddDiaryEntry.EXTRA_CIRCULAR_REVEAL_Y,revealY);
        intent.putExtra("loggedUser",MainActivity.loggedUser);

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
            }
        });
    }

    @Override
    public void onClick(DiaryItem diaryItem) {
        viewModel.delete(diaryItem);
        updateList();
    }
}

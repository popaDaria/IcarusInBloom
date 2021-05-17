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
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.otaliastudios.cameraview.BitmapCallback;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.FileCallback;
import com.otaliastudios.cameraview.PictureResult;
import com.squareup.picasso.Picasso;
import com.sunny.icarusinbloom.model.CapturePhotoUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

//SurfaceHolder.Callback, Handler.Callback
public class Fragment3 extends Fragment {

    View rootView;
    Button photoBtn;
    ImageView imageView;
    ImageView refresh;
    private static final int TAKE_IMAGE = 133;
    CameraView cameraView;

    String currentPhotoPath;
    File photoFile = null;

    private File createImageFile() {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cameraView = rootView.findViewById(R.id.cameraView);
        cameraView.setLifecycleOwner(getViewLifecycleOwner());

        cameraView.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(@NonNull PictureResult result) {
                System.out.println("picture taken");
                result.toBitmap(new BitmapCallback() {
                    @Override
                    public void onBitmapReady(@Nullable Bitmap bitmap) {
                       // imageView.setImageBitmap(bitmap);


                        /*String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        String imageFileName = "JPEG_" + timeStamp + "_";
                        CapturePhotoUtils.insertImage(getContext().getContentResolver(),bitmap,imageFileName,"InBloom_diary");*/
                    }
                });

                photoFile = createImageFile();
                result.toFile(photoFile, new FileCallback() {
                    @Override
                    public void onFileReady(@Nullable File file) {
                        if (photoFile != null) {
                            Uri photoURI = FileProvider.getUriForFile(getContext(),
                                    "com.example.android.fileprovider",
                                    photoFile);
                            System.out.println(photoURI.toString());
                            Picasso.get().load(photoURI).rotate(90)
                                    .centerCrop().fit().into(imageView);
                            imageView.setVisibility(View.VISIBLE);
                            cameraView.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment3,container,false);

        photoBtn = rootView.findViewById(R.id.takePhoto);
        imageView = rootView.findViewById(R.id.takenImageView);
        refresh = rootView.findViewById(R.id.refreshImage);
        photoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.takePicture();
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setVisibility(View.INVISIBLE);
                cameraView.setVisibility(View.VISIBLE);
            }
        });

        return rootView;
    }


}

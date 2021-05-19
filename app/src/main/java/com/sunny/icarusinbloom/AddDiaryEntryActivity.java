package com.sunny.icarusinbloom;

import android.animation.Animator;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;

import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.FileCallback;
import com.otaliastudios.cameraview.PictureResult;
import com.squareup.picasso.Picasso;
import com.sunny.icarusinbloom.recycler_elem.DiaryItem;
import com.sunny.icarusinbloom.login.User;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddDiaryEntryActivity extends AppCompatActivity {

    Button photoBtn;
    ImageView imageView;
    CardView cardView;
    ImageView refresh;
    CameraView cameraView;

    String currentPhotoPath;
    File photoFile = null;

    View rootLayout;
    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";
    private int revealX;
    private int revealY;
    private User user;

    private String content;
    private String date;
    private String imageUri;

    private Button addEntry;
    private EditText contentView;

    private File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,
                    ".jpg",
                    storageDir
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diary);

        final Intent intent1 = getIntent();
        rootLayout = findViewById(R.id.diaryAddParent);

        if(savedInstanceState==null && Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP
                &&intent1.hasExtra(EXTRA_CIRCULAR_REVEAL_X) && intent1.hasExtra(EXTRA_CIRCULAR_REVEAL_Y)){
            rootLayout.setVisibility(View.INVISIBLE);

            revealX = intent1.getIntExtra(EXTRA_CIRCULAR_REVEAL_X,0);
            revealY = intent1.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y,0);
            user = (User) intent1.getExtras().getSerializable("loggedUser");

            ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
            if(viewTreeObserver.isAlive()){
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        revealActivity(revealX,revealY);
                        rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }
        }else{
            rootLayout.setVisibility(View.VISIBLE);
        }

        cameraView = findViewById(R.id.cameraView);
        cameraView.setLifecycleOwner(this);

        addEntry = findViewById(R.id.saveEntry);
        contentView = findViewById(R.id.contentDiaryAdd);

        addEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!contentView.getText().toString().isEmpty()){
                    content = contentView.getText().toString();
                }else{
                    content="null";
                }
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date today = new Date();
                date = sdf.format(today);
                if(imageUri ==null){
                    imageUri="null";
                }

                if(!content.equals("null")) {
                    DiaryItem diaryItem = new DiaryItem(date, imageUri, content, user.getId());
                    Intent intent = new Intent();
                    intent.putExtra("entryAdded", diaryItem);
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), "please input a content", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        photoBtn = findViewById(R.id.takePhoto);
        imageView = findViewById(R.id.takenImageView);
        cardView = findViewById(R.id.diaryCardView);
        refresh = findViewById(R.id.refreshImage);
        photoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.takePicture();
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView.setVisibility(View.INVISIBLE);
                cameraView.setVisibility(View.VISIBLE);
            }
        });

        cameraView.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(@NonNull PictureResult result) {
                photoFile = createImageFile();
                result.toFile(photoFile, new FileCallback() {
                    @Override
                    public void onFileReady(@Nullable File file) {
                        if (photoFile != null) {
                            Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
                                    "com.example.android.fileprovider",
                                    photoFile);
                            imageUri=photoURI.toString();
                            Picasso.get().load(photoURI).rotate(90).centerCrop().fit().into(imageView);
                            cardView.setVisibility(View.VISIBLE);
                            cameraView.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });
    }

    protected void revealActivity(int x,int y){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            float finalRadius = (float) (Math.max(rootLayout.getWidth(),rootLayout.getHeight()) *1.1);

            Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout,x,y,0,finalRadius);
            circularReveal.setDuration(310);
            circularReveal.setInterpolator(new AccelerateInterpolator());

            rootLayout.setVisibility(View.VISIBLE);
            circularReveal.start();
        }else {
            finish();
        }
    }
}

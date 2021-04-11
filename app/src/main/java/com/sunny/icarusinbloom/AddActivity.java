package com.sunny.icarusinbloom;

import android.animation.Animator;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.squareup.picasso.Picasso;
import com.sunny.icarusinbloom.login.User;
import com.sunny.icarusinbloom.login.UserViewModel;
import com.sunny.icarusinbloom.recycler.PlantItem;

public class AddActivity extends AppCompatActivity {
    String plantName="null";
    String plantInfo="null";
    String plantSpecies="null";
    String plantUri = "null";
    String plantBday = "null";

    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";

    View rootLayout;
    private int revealX;
    private int revealY;
    private User user;
    private int userId;
    //private UserViewModel userViewModel;
    PlantItem toAdd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

       // userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        final Intent intent1 = getIntent();
        rootLayout = findViewById(R.id.mainAddLayout);
        if(savedInstanceState==null && Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP
        &&intent1.hasExtra(EXTRA_CIRCULAR_REVEAL_X) && intent1.hasExtra(EXTRA_CIRCULAR_REVEAL_Y)){
            rootLayout.setVisibility(View.INVISIBLE);

            revealX = intent1.getIntExtra(EXTRA_CIRCULAR_REVEAL_X,0);
            revealY = intent1.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y,0);
            user = (User) intent1.getExtras().getSerializable("loggedUser");

/*            userViewModel.getUserId(user.getEmail()).observe(this, x->{
                if(x!=null){
                    userId = x;
                }
            });*/

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

        EditText name = findViewById(R.id.plantNameEdit);
        EditText info = findViewById(R.id.plantInfoEdit);
        EditText species = findViewById(R.id.plantSpeciesEdit);
        EditText bday = findViewById(R.id.plantBdayEdit);

        TextView button = findViewById(R.id.saveChangesAdd);
        Button addPlantImg = findViewById(R.id.addPlantImageButton);

        addPlantImg.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
           // intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,2);
        });

        button.setOnClickListener(v->{
            if(!info.getText().toString().isEmpty())
                plantInfo=info.getText().toString();
            if(!name.getText().toString().isEmpty())
                plantName=name.getText().toString();
            if(!species.getText().toString().isEmpty())
                plantSpecies=species.getText().toString();
            if(!bday.getText().toString().isEmpty()){
                plantBday=bday.getText().toString();
            }

            if(plantName.equals("null")|| plantSpecies.equals("null")) {
                Toast toast = Toast.makeText(this, "please input a name & species", Toast.LENGTH_SHORT);
                toast.show();
            }else {
                toAdd = new PlantItem(plantName, plantInfo, plantSpecies, plantUri, plantBday,user.getId());
                Toast toast = Toast.makeText(this,"USER ID:"+ userId,Toast.LENGTH_LONG);
                toast.show();
                System.out.println(toAdd.toString());
                Intent intent = new Intent();
                intent.putExtra("plantAdded", toAdd);
                setResult(RESULT_OK, intent);
            }
            finish();

            /*Toast toast = Toast.makeText(this,"info sent:"+ toAdd.toString(),Toast.LENGTH_LONG);
            toast.show();*/
        });
    }

    protected void revealActivity(int x,int y){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            float finalRadius = (float) (Math.max(rootLayout.getWidth(),rootLayout.getHeight()) *1.1);

            Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout,x,y,0,finalRadius);
            circularReveal.setDuration(400);
            circularReveal.setInterpolator(new AccelerateInterpolator());

            rootLayout.setVisibility(View.VISIBLE);
            circularReveal.start();
        }else {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2){
            if(resultCode==RESULT_OK){
                plantUri=data.getData().toString();
                Picasso.get().load(plantUri).centerCrop().fit().into((ImageView)findViewById(R.id.addPlantImageView));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    this.getContentResolver().takePersistableUriPermission(data.getData(), Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
            }
        }
    }
}

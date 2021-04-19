package com.sunny.icarusinbloom;

import android.animation.Animator;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
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
import com.sunny.icarusinbloom.webservice.PlantApi;
import com.sunny.icarusinbloom.webservice.ServiceGenerator;
import com.sunny.icarusinbloom.webservice.SpeciesInfo;
import com.sunny.icarusinbloom.webservice.SpeciesResponse;
import com.sunny.icarusinbloom.webservice.SpeciesSearchResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddActivity extends AppCompatActivity {
    String plantName="null";
    String plantInfo="null";
    String plantSpecies="null";
    String plantUri = "null";
    String plantBday = "null";
    int speciesId = -1;
    int water_interval=3;
    String water_type="normal";

    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";

    View rootLayout;
    private int revealX;
    private int revealY;
    private User user;
    //private int userId;
    PlantItem toAdd;
    SpeciesInfo speciesInfo;
    TextView button;
    TextView warning;

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

        button = findViewById(R.id.saveChangesAdd);
        button.setVisibility(View.INVISIBLE);
        warning = findViewById(R.id.warningTextAddPlant);
        warning.setVisibility(View.VISIBLE);
        Button addPlantImg = findViewById(R.id.addPlantImageButton);
        ImageView plantPlaceholder = findViewById(R.id.addPlantImageView);
        plantPlaceholder.setImageResource(R.drawable.plant);

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
                setSpecies(rootLayout);
                toAdd = new PlantItem(plantName, plantInfo, plantSpecies, plantUri, plantBday,user.getId(),speciesId,water_interval,water_type);
                //Toast toast = Toast.makeText(this,"USER ID:"+ userId,Toast.LENGTH_LONG);
                //toast.show();
                System.out.println(toAdd.toString());
                Intent intent = new Intent();
                intent.putExtra("plantAdded", toAdd);
                if(speciesInfo!=null){
                    System.out.println("ADDING SPECIES INFO");
                    intent.putExtra("speciesAdded",speciesInfo);
                }
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

    public void setSpecies(View view) {
        EditText species = findViewById(R.id.plantSpeciesEdit);
        if(!species.getText().toString().isEmpty()) {
            plantSpecies = species.getText().toString();
            System.out.println("TYPED SPECIES");
        }

        String token = "De-v8aTD7o019T5P3T_ReGeB27zMkNdmb9rga5EhGCQ";
        PlantApi plantApi = ServiceGenerator.getPlantApi();
        if(plantSpecies!=null){
            Call<SpeciesSearchResponse> call = plantApi.searchForSpecies(plantSpecies,token);
            call.enqueue(new Callback<SpeciesSearchResponse>() {
                @Override
                public void onResponse(Call<SpeciesSearchResponse> call, Response<SpeciesSearchResponse> response) {
                    if(response.code()==200){
                        speciesId=response.body().getSpeciesId();
                        setSpeciesInfo(speciesId);
                    }else{
                        System.out.println("RESPONSE CODE NOT OKAY WHEN SEARCHING:"+response.code()+" "+response.body());
                    }
                }

                @Override
                public void onFailure(Call<SpeciesSearchResponse> call, Throwable t) {
                    speciesId=-1;
                    System.out.println("SEARCH FAILURE");
                }
            });
        }
        warning.setVisibility(View.INVISIBLE);
        button.setVisibility(View.VISIBLE);
    }

    private void setSpeciesInfo(int speciesId){
        String token = "De-v8aTD7o019T5P3T_ReGeB27zMkNdmb9rga5EhGCQ";
        PlantApi plantApi = ServiceGenerator.getPlantApi();
        if(speciesId!=-1){
            Call<SpeciesResponse> call = plantApi.getSpeciesInfo(speciesId,token);
            call.enqueue(new Callback<SpeciesResponse>() {
                @Override
                public void onResponse(Call<SpeciesResponse> call, Response<SpeciesResponse> response) {
                    if(response.code()==200){
                        speciesInfo = response.body().getPlantInfo();
                        calculateWaterNeed(speciesInfo);
                        System.out.println(speciesInfo.toString());
                        System.out.println("Species Info: "+speciesInfo.toString());
                    }else{
                        System.out.println("RESPONSE CODE NOT OKAY WHEN GETTING INFO:"+response.code()+" "+response.body());
                    }
                }

                @Override
                public void onFailure(Call<SpeciesResponse> call, Throwable t) {
                    //nothing
                    System.out.println("GETTING INFO FAILURE");
                }
            });
        }else{
            water_interval=3;
            water_type="normal";
        }
    }

    private void calculateWaterNeed(SpeciesInfo speciesInfo){
        if(speciesInfo.getMinimumPe()!=0||speciesInfo.getMaximumPe()!=0){
            double waterNeed =(double) (speciesInfo.getMaximumPe() + speciesInfo.getMinimumPe()) / 2 /64;
            double pe = (double) waterNeed/10;
            if(pe<0.5){
                water_interval=3;
                water_type="scarcely";
            }else if(pe>=0.5&&pe<1){
                water_interval=3;
                water_type="normal";
            }else if(pe>=1&&pe<1.5){
                water_interval=3;
                water_type="abundant";
            }else if(pe>=1.5&&pe<2.5){
                water_interval=2;
                water_type="normal";
            }else{
                water_interval=2;
                water_type="abundant";
            }
        }else{
            water_interval=3;
            water_type="normal";
        }
    }
}

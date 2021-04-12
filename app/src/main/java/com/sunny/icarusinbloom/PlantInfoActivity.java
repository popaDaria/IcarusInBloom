package com.sunny.icarusinbloom;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.sunny.icarusinbloom.recycler.PlantItem;

public class PlantInfoActivity extends AppCompatActivity {

    PlantItem plant;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_plant_info);

        TextView backButton = findViewById(R.id.goBackToFragment);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        plant = (PlantItem) bundle.getSerializable("plant");

        TextView header = findViewById(R.id.plantInfoViewTitle);
        TextView info = findViewById(R.id.plantInfoViewInfo);
        TextView species = findViewById(R.id.plantInfoViewSpecies);
        TextView bday = findViewById(R.id.plantInfoViewBday);
        //TODO: use the extra field for another info
        TextView extra = findViewById(R.id.plantInfoViewExtra);
        ImageView image = findViewById(R.id.plantImageInfoView);

        Picasso.get().load(Uri.parse(plant.getImage())).centerCrop().fit().into(image);
        String desc ="Plant description: "+plant.getInfo();
        String spec = "Plant species: "+plant.getSpecies();
        String bd = "Plant birthday: "+plant.getBday();
        info.setText(desc);
        species.setText(spec);
        bday.setText(bd);
        String headerText = plant.getName();
        header.setText(headerText);


       backButton.setOnClickListener(v->{
            finish();
        });

    }

    public void exitView(View view) {
       finish();
    }

    public void deletePlant(View view) {
        Intent intent = new Intent();
        intent.putExtra("deletePlant", plant);
        setResult(RESULT_OK, intent);
        finish();
    }
}

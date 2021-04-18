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
import com.sunny.icarusinbloom.webservice.SpeciesInfo;

public class PlantInfoActivity extends AppCompatActivity {

    PlantItem plant;
    SpeciesInfo speciesInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_plant_info);

        TextView backButton = findViewById(R.id.goBackToFragment);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        plant = (PlantItem) bundle.getSerializable("plant");
        speciesInfo = (SpeciesInfo) bundle.getSerializable("species");

        TextView header = findViewById(R.id.plantInfoViewTitle);
        TextView info = findViewById(R.id.plantInfoViewInfo);
        TextView species = findViewById(R.id.plantInfoViewSpecies);
        TextView bday = findViewById(R.id.plantInfoViewBday);
        TextView extra = findViewById(R.id.plantInfoViewExtra);
        ImageView image = findViewById(R.id.plantImageInfoView);

        Picasso.get().load(Uri.parse(plant.getImage())).centerCrop().fit().into(image);
        String desc = "Plant description: " + plant.getInfo();
        String spec = "Plant species: " + plant.getSpecies();
        String bd = "Plant birthday: " + plant.getBday();
        info.setText(desc);
        species.setText(spec);
        bday.setText(bd);
        String headerText = plant.getName();
        header.setText(headerText);

        String extraDesc = "\bEXTRA INFO:\b\n"+"\u2022 WATERING TYPE: "+plant.getWater_type()+ "\n\u2022 WATERING INTERVAL: every "+plant.getWater_interval()+" days \n";
        if (speciesInfo != null) {
            extraDesc += "\u2022 "+speciesInfo.getScientificName();
            //System.out.println(extraDesc);
            if (speciesInfo.isEdible()) {
                extraDesc += ", EDIBLE \n";
            } else extraDesc += ", NOT EDIBLE \n";
            if (speciesInfo.getGrowthHabit() != null)
                extraDesc += "\u2022 GROWTH HABIT: " + speciesInfo.getGrowthHabit() + "\n";
            if (speciesInfo.getGrowthRate() != null)
                extraDesc += "\u2022 GROWTH RATE: " + speciesInfo.getGrowthRate() + "\n";
            if (speciesInfo.getMinimumTemp() != 0)
                extraDesc += "\u2022 MINIMUM TEMP: " + speciesInfo.getMinimumTemp() + "\n";
            if (speciesInfo.getSoilLevel() != -1)
                extraDesc += "\u2022 PREFERRED SOIL NUTRIMENTS LEVEL(0-10): "+speciesInfo.getSoilLevel();
        }
        extra.setText(extraDesc);


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

    public void nothing(View view) {
        //do nothing
    }
}

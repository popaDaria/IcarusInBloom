package com.sunny.icarusinbloom;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;
import com.sunny.icarusinbloom.login.User;
import com.sunny.icarusinbloom.tabs.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity{

    public static User loggedUser;
    //private UserViewModel userViewModel;
    User user;

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImageView userPic = findViewById(R.id.userImage);
        switch (loggedUser.getPhotoPref()){
            case "male":
                Picasso.get().load(R.drawable.male_icon_farmer).centerCrop().fit().into(userPic);
                break;
            case "female":
                Picasso.get().load(R.drawable.female_farmer).centerCrop().fit().into(userPic);
                break;
            default:
                Picasso.get().load(R.drawable.other_icon).centerCrop().fit().into(userPic);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* View thisView = findViewById(R.id.mainActivityLayout);
        thisView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);*/

        TextView titleHeader= findViewById(R.id.title_header);

        loggedUser = LogInActivity.loggedUser;
        if(loggedUser==null){
            loggedUser = SignUpActivity.signedUpUser;
        }

        String newTitle = "Hello, "+loggedUser.getFirstName()+" c:";
        titleHeader.setText(newTitle);
        ImageView userPic = findViewById(R.id.userImage);
        switch (loggedUser.getPhotoPref()){
            case "male":
                Picasso.get().load(R.drawable.male_icon_farmer).centerCrop().fit().into(userPic);
                break;
            case "female":
                Picasso.get().load(R.drawable.female_farmer).centerCrop().fit().into(userPic);
                break;
            default:
                Picasso.get().load(R.drawable.other_icon).centerCrop().fit().into(userPic);
                break;
        }

       // userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabsLayout);
        tabs.setupWithViewPager(viewPager);

        Toolbar toolbar = findViewById(R.id.toolbarHeader);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //System.out.println(loggedUser.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.action_settings)
        {
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }else if(item.getItemId()==R.id.action_logOut){
            Intent intent = new Intent(this,LogInActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
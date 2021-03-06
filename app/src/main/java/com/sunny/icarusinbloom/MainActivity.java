package com.sunny.icarusinbloom;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;
import com.sunny.icarusinbloom.login.User;
import com.sunny.icarusinbloom.notifications.AlarmReceiver;
import com.sunny.icarusinbloom.tabs.SectionsPagerAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity{

    public static User loggedUser;
    private PendingIntent pendingIntent;
    boolean showGif = false;

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

        TextView titleHeader= findViewById(R.id.title_header);
        String newTitle = "Hello, "+loggedUser.getName()+" c:";
        titleHeader.setText(newTitle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView titleHeader= findViewById(R.id.title_header);

        loggedUser = LogInActivity.loggedUser;
        if(loggedUser==null){
            loggedUser = SignUpActivity.signedUpUser;
        }

        String newTitle = "Hello, "+loggedUser.getName()+" c:";
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

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabsLayout);
        tabs.setupWithViewPager(viewPager);

        Toolbar toolbar = findViewById(R.id.toolbarHeader);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        Intent alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);

        start();
        startAt8();


        GifImageView gifView = findViewById(R.id.gif);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
        Date today = new Date();
        if(sdf.format(today).equals(MainActivity.loggedUser.getBday().substring(0,5))){
            showGif=true;
        }

        if(showGif){
            gifView.setVisibility(View.VISIBLE);
            gifView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    gifView.setVisibility(View.INVISIBLE);
                }
            },4000);
        }
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

    public void start() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 1000 * 60 * 60 * 6;
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        Log.e("Alarm","started");
    }

    public void startAt8() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 1000 * 60 * 60 * 12;

        /* Set the alarm to start at 8 AM */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);

        /* Repeating on every 12h interval */
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent);
    }
}
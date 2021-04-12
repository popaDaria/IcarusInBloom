package com.sunny.icarusinbloom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;
import com.sunny.icarusinbloom.login.User;
import com.sunny.icarusinbloom.login.UserViewModel;
import com.sunny.icarusinbloom.persistance.UserRepository;
import com.sunny.icarusinbloom.recycler.PlantItem;
import com.sunny.icarusinbloom.recycler.PlantItemAdapter;
import com.sunny.icarusinbloom.recycler.PlantItemViewModel;
import com.sunny.icarusinbloom.tabs.SectionsPagerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    public static User loggedUser;
    //private UserViewModel userViewModel;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView userPic = findViewById(R.id.userImage);
        //TODO: change to user pic preference
        Picasso.get().load(R.drawable.succulent).centerCrop().fit().into(userPic);
        TextView titleHeader= findViewById(R.id.title_header);

        loggedUser = LogInActivity.loggedUser;
        if(loggedUser==null){
            loggedUser = SignUpActivity.signedUpUser;
        }

        String newTitle = "Hello, "+loggedUser.getFirstName()+" c:";
        titleHeader.setText(newTitle);
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
            //TODO: go to settings menu
            return true;
        }else if(item.getItemId()==R.id.action_logOut){
            Intent intent = new Intent(this,LogInActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
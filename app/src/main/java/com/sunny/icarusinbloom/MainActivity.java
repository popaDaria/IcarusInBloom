package com.sunny.icarusinbloom;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
    private UserViewModel userViewModel;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabsLayout);
        tabs.setupWithViewPager(viewPager);

        ImageView userPic = findViewById(R.id.userImage);
        //TODO: change to user pic & text
        Picasso.get().load(R.drawable.succulent).centerCrop().fit().into(userPic);
        TextView titleHeader= findViewById(R.id.title_header);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        user = (User) bundle.getSerializable("NewLoggedUser");
        if(user==null){
            user= (User) bundle.getSerializable("LoggedUser");
        }

        userViewModel.getAllUsers().observe(this,users->{
            if(!users.isEmpty()){
                for (User u:users) {
                    if(u.getEmail().equals(user.getEmail())){
                       // System.out.println(u.toString());
                        loggedUser = u;
                        System.out.println("LOGGED IN AS:"+loggedUser.toString());
                        titleHeader.setText("Hello, "+loggedUser.getFirstName()+" c:");
                        break;
                    }
                }
            }
        });


        //System.out.println(loggedUser.toString());
    }
}
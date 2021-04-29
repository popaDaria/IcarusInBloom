package com.sunny.icarusinbloom;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.Preference;

import com.sunny.icarusinbloom.login.UserViewModel;

public class SettingsActivity extends AppCompatActivity {

    UserViewModel userViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.settings_container, new SettingsFragment()).
                commit();

    }
}
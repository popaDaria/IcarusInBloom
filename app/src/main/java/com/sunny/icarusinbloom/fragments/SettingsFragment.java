package com.sunny.icarusinbloom.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.sunny.icarusinbloom.MainActivity;
import com.sunny.icarusinbloom.R;
import com.sunny.icarusinbloom.login.UserViewModel;

public class SettingsFragment extends PreferenceFragmentCompat {

    private static int photoGender;
    private static String mailsPref;
    private UserViewModel userViewModel;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings,rootKey);

        Preference name = findPreference("name");
        name.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String newName = newValue.toString();
                MainActivity.loggedUser.setName(newName);
                userViewModel.update(MainActivity.loggedUser);
                return true;
            }
        });

        Preference feedback= findPreference("feedback");
        feedback.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                String data = "mailto:" +Uri.encode("popadaria19@gmail.com")+
                        "?subject="+Uri.encode("Feedback for InBloom app");
                intent.setData(Uri.parse(data));
                startActivity(intent);
                return true;
            }
        });

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        Preference gender = findPreference("gender");
        gender.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                photoGender= Integer.parseInt(newValue.toString());
                String pref;
                switch (photoGender){
                    case 1:
                        pref="male";
                        break;
                    case 2:
                        pref="female";
                        break;
                    default:
                        pref="other";
                        break;
                }
                MainActivity.loggedUser.setPhotoPref(pref);
                userViewModel.update(MainActivity.loggedUser);
                return true;
            }
        });

        Preference mails = findPreference("mails");
        mails.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                mailsPref = newValue.toString();
                MainActivity.loggedUser.setMailPref(mailsPref);
                userViewModel.update(MainActivity.loggedUser);
                return true;
            }
        });

    }
}

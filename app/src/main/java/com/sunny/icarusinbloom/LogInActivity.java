package com.sunny.icarusinbloom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.sunny.icarusinbloom.login.User;
import com.sunny.icarusinbloom.login.UserViewModel;

import java.util.List;

public class LogInActivity extends AppCompatActivity {

    private String userEmail =null;
    private String userPassword=null;
    private UserViewModel userViewModel;
    private EditText email;
    private EditText password;
    public static User loggedUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        email = findViewById(R.id.userEmailLogin);
        password = findViewById(R.id.userPasswordLogin);

        SharedPreferences prefs = getSharedPreferences("MyPrefs",MODE_PRIVATE);
        userEmail = prefs.getString("userEmail","");
        userPassword = prefs.getString("userPassword","");
        if(userEmail!=null&&userPassword!=null){
            email.setText(userEmail);
            password.setText(userPassword);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("userEmail", email.getText().toString());
        editor.putString("userPassword",password.getText().toString());
        editor.apply();
    }

    public void goToSignUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void login(View view) {

        if(!email.getText().toString().isEmpty())
            userEmail=email.getText().toString();
        if(!password.getText().toString().isEmpty())
            userPassword=password.getText().toString();

        if(userEmail!=null&&userPassword!=null){
            userViewModel.getAllUsers().observe(this, users->{
            if(!users.isEmpty()) {
                boolean found = false;
                for (User u : users) {
                    if (u.getEmail().equals(userEmail) && u.getPassword().equals(userPassword)) {
                        found=true;
                        loggedUser=u;
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        break;
                    }
                }
                if(!found) {
                    Toast toast = Toast.makeText(this, "incorrect email or password", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }else{
                Toast toast = Toast.makeText(this, "no user accounts found. please create a new one first", Toast.LENGTH_SHORT);
                toast.show();
            }
            });
        }else {
            Toast toast = Toast.makeText(this, "please input an email & password", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}

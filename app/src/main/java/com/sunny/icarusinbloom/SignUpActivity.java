package com.sunny.icarusinbloom;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.sunny.icarusinbloom.login.User;
import com.sunny.icarusinbloom.login.UserViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SignUpActivity extends AppCompatActivity {

    String userEmail = "null";
    String userPassword = "null";
    String userFirstName = "null";
    String userBday = "null";
    boolean uniqueEmail = true;
    public static User signedUpUser;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        TextView button = findViewById(R.id.saveChangesSignUp);

        EditText email = findViewById(R.id.userEmailEdit);
        EditText password = findViewById(R.id.userPasswordEdit);
        EditText firstName = findViewById(R.id.userFirstNameEdit);
        EditText bday = findViewById(R.id.userBdayEdit);

        DatePickerDialog.OnDateSetListener dateSetListener =
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        userBday = String.format("%02d/%02d/%02d",dayOfMonth,month+1,year);
                        bday.setText(userBday);
                    }
                };
        bday.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SignUpActivity.this,dateSetListener, 1990,0,1).show();
            }
        });


        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        button.setOnClickListener(v->{
            if(!email.getText().toString().isEmpty())
                userEmail=email.getText().toString();
            if(!password.getText().toString().isEmpty())
                userPassword=password.getText().toString();
            if(!firstName.getText().toString().isEmpty())
                userFirstName=firstName.getText().toString();

            if(!userPassword.equals("null") && !userEmail.equals("null")) {
                userViewModel.getAllUsers().observe(this,users->{
                    if(!users.isEmpty()){
                        for (User u:users) {
                            if(u.getEmail().equals(userEmail))
                                uniqueEmail=false;
                        }
                    }
                });
                if(uniqueEmail) {
                    User user = new User(userFirstName, userPassword, userEmail, userBday, "other","true");
                    userViewModel.insert(user);

                    userViewModel.getAllUsers().observe(this,users->{
                        if(!users.isEmpty()){
                            for (User u:users) {
                                if(u.getEmail().equals(user.getEmail())){
                                    signedUpUser = u;
                                    LogInActivity.loggedUser=null;
                                    Intent intent = new Intent(this, MainActivity.class);
                                    startActivity(intent);
                                    break;
                                }
                            }
                        }
                    });

                }else{
                    Toast toast = Toast.makeText(this, "an account with this email already exists", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            else{
                Toast toast = Toast.makeText(this, "please input an email & password", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }

    public void goToLogIn(View view) {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }
}

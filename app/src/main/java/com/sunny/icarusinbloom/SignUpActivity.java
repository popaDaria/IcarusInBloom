package com.sunny.icarusinbloom;

import android.content.Intent;
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

public class SignUpActivity extends AppCompatActivity {

    String userEmail = "null";
    String userPassword = "null";
    String userFirstName = "null";
    String userLastName = "null";
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
        EditText lastName = findViewById(R.id.userLastNameEdit);
        EditText bday = findViewById(R.id.userBdayEdit);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);


     /*   userViewModel.getAllUsers().observe(this,users->{
            if(!users.isEmpty()){
                for (User u:users) {
                    System.out.println(u.toString());
                }
            }
        });*/

        button.setOnClickListener(v->{
            if(!email.getText().toString().isEmpty())
                userEmail=email.getText().toString();
            if(!password.getText().toString().isEmpty())
                userPassword=password.getText().toString();
            if(!firstName.getText().toString().isEmpty())
                userFirstName=firstName.getText().toString();
            if(!lastName.getText().toString().isEmpty())
                userLastName=lastName.getText().toString();
            if(!bday.getText().toString().isEmpty())
                userBday=bday.getText().toString();

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
                    User user = new User(userFirstName, userLastName, userPassword, userEmail, userBday);
                    userViewModel.insert(user);

                    userViewModel.getAllUsers().observe(this,users->{
                        if(!users.isEmpty()){
                            for (User u:users) {
                                if(u.getEmail().equals(user.getEmail())){
                                    // System.out.println(u.toString());
                                    signedUpUser = u;
                                    LogInActivity.loggedUser=null;
                                    Intent intent = new Intent(this, MainActivity.class);
                                    //intent.putExtra("NewLoggedUser", user);
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

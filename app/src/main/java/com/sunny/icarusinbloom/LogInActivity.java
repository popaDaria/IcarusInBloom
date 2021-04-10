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

import java.util.List;

public class LogInActivity extends AppCompatActivity {

    private String userEmail =null;
    private String userPassword=null;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

    }

    public void goToSignUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void login(View view) {
        EditText email = findViewById(R.id.userEmailLogin);
        EditText password = findViewById(R.id.userPasswordLogin);
        if(!email.getText().toString().isEmpty())
            userEmail=email.getText().toString();
        if(!password.getText().toString().isEmpty())
            userPassword=password.getText().toString();

        if(userEmail!=null&&userPassword!=null){
            //List<User> users = userViewModel.getAllUsers().getValue();

            userViewModel.getAllUsers().observe(this, users->{
            if(!users.isEmpty()) {
                boolean found = false;
                for (User u : users) {
                    if (u.getEmail().equals(userEmail) && u.getPassword().equals(userPassword)) {
                        //System.out.println(u.toString());
                        found=true;
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.putExtra("LoggedUser", u);
                        startActivity(intent);
                        break;
                    }
                }
                if(!found) {
                    Toast toast = Toast.makeText(this, "incorrect email or password", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            });
        }else {
            Toast toast = Toast.makeText(this, "please input an email & password", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}

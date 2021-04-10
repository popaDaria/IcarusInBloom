package com.sunny.icarusinbloom.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sunny.icarusinbloom.persistance.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private final UserRepository repository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = UserRepository.getInstance(application);
    }

    public LiveData<List<User>> getAllUsers(){
        return repository.getAllUsers();
    }
    public void insert(User user){
        repository.insert(user);
    }
    public LiveData<Integer> getUserId(String email){
        return repository.getUserId(email);
    }
}

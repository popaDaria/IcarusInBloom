package com.sunny.icarusinbloom.persistance;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.sunny.icarusinbloom.login.User;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {
    private static UserRepository instance;
    private final UserDao userDao;
    private final ExecutorService executorService;

    private UserRepository(Application application){
        UsersDB db = UsersDB.getInstance(application);
        userDao = db.userDao();
        executorService = Executors.newFixedThreadPool(1);
    }

    public static synchronized UserRepository getInstance(Application application){
        if(instance==null)
            instance= new UserRepository(application);
        return instance;
    }

    public LiveData<List<User>> getAllUsers(){
        return userDao.getAllUsers();
    }
    public void insert(User user){
        executorService.execute(()->userDao.insert(user));
    }
    public LiveData<Integer> getUserId(String email){
        return userDao.getUserId(email);
    }
    public void update(User user){
        executorService.execute(()->userDao.update(user));
    }

}

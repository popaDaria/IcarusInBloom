package com.sunny.icarusinbloom.persistance;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.sunny.icarusinbloom.login.User;
import com.sunny.icarusinbloom.recycler.PlantItem;

@Database(entities = {User.class}, version = 4)
public abstract class UsersDB extends RoomDatabase {

    private static UsersDB instance;

    public abstract UserDao userDao();

    public static synchronized UsersDB getInstance(Context context){
        if(instance == null) {
            instance = Room.databaseBuilder(context,
                    UsersDB.class, "Users_DB")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}

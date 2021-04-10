package com.sunny.icarusinbloom.persistance;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.sunny.icarusinbloom.login.User;
import com.sunny.icarusinbloom.recycler.PlantItem;

@Database(entities = {PlantItem.class},version = 7)
public abstract class PlantDB extends RoomDatabase {

    private static PlantDB instance;

    public abstract PlantDao plantDao();

    public static synchronized PlantDB getInstance(Context context){
        if(instance == null) {
            instance = Room.databaseBuilder(context,
                    PlantDB.class, "Plants_DB2")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}

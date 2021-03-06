package com.sunny.icarusinbloom.persistance;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.sunny.icarusinbloom.recycler_elem.PlantItem;

@Database(entities = {PlantItem.class},version = 3)
public abstract class PlantDB extends RoomDatabase {

    private static PlantDB instance;

    public abstract PlantDao plantDao();

    public static synchronized PlantDB getInstance(Context context){
        if(instance == null) {
            instance = Room.databaseBuilder(context,
                    PlantDB.class, "plants")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}

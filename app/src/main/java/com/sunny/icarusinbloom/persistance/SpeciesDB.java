package com.sunny.icarusinbloom.persistance;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.sunny.icarusinbloom.webservice.SpeciesInfo;

@Database(entities = {SpeciesInfo.class},version = 1)
public abstract class SpeciesDB extends RoomDatabase {

    private static SpeciesDB instance;

    public abstract SpeciesDao plantDao();

    public static synchronized SpeciesDB getInstance(Context context){
        if(instance == null) {
            instance = Room.databaseBuilder(context,
                    SpeciesDB.class, "species")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}

package com.sunny.icarusinbloom.persistance;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.sunny.icarusinbloom.recycler_elem.DiaryItem;

@Database(entities = {DiaryItem.class},version = 1)
public abstract class DiaryDB extends RoomDatabase {

    private static DiaryDB instance;

    public abstract DiaryDao diaryDAO();

    public static synchronized DiaryDB getInstance(Context context){
        if(instance == null) {
            instance = Room.databaseBuilder(context,
                    DiaryDB.class, "diaries")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}

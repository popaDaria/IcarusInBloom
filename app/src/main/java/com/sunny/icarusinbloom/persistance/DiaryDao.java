package com.sunny.icarusinbloom.persistance;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.sunny.icarusinbloom.diary.DiaryItem;
import com.sunny.icarusinbloom.recycler.PlantItem;

import java.util.List;

@Dao
public interface DiaryDao {

    @Insert
    void insert(DiaryItem item);

    @Update
    void update(DiaryItem item);

    @Delete
    void delete(DiaryItem item);

    @Query("SELECT * FROM DiaryEntries")
    LiveData<List<DiaryItem>> getAllDiaryEntries();

    @Query("SELECT * FROM DiaryEntries WHERE user_id= :userId")
    LiveData<List<DiaryItem>> getAllDiaryEntriesForUSer(int userId);
}

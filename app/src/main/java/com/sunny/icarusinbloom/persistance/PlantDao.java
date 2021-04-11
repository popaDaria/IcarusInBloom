package com.sunny.icarusinbloom.persistance;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.sunny.icarusinbloom.login.User;
import com.sunny.icarusinbloom.recycler.PlantItem;

import java.util.List;

@Dao
public interface PlantDao {

    @Insert
    void insert(PlantItem item);

    @Update
    void update(PlantItem item);

    @Delete
    void delete(PlantItem item);

    @Query("SELECT * FROM Plants")
    LiveData<List<PlantItem>> getAllPlants();

    @Query("SELECT * FROM Plants WHERE plantId= :id")
    LiveData<PlantItem> getPlant(int id);

    @Query("SELECT * FROM Plants WHERE ownerId= :userId")
    LiveData<List<PlantItem>> getAllUserPlant(int userId);

}

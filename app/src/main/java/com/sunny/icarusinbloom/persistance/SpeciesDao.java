package com.sunny.icarusinbloom.persistance;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.sunny.icarusinbloom.webservice.SpeciesInfo;

import java.util.List;

@Dao
public interface SpeciesDao {
    @Insert
    void insert(SpeciesInfo item);

    @Update
    void update(SpeciesInfo item);

    @Delete
    void delete(SpeciesInfo item);

    @Query("SELECT * FROM Species")
    LiveData<List<SpeciesInfo>> getAllSpecies();

    @Query("SELECT * FROM Species WHERE speciesId= :id")
    LiveData<SpeciesInfo> getSpeciesById(int id);
}

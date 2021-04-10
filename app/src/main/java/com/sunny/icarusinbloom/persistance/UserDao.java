package com.sunny.icarusinbloom.persistance;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.sunny.icarusinbloom.login.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM Users")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM Users WHERE id= :userId")
    User getUser(int userId);

    @Query("SELECT id from Users where email=:email")
    LiveData<Integer> getUserId(String email);

    @Transaction
    @Query("SELECT * FROM Users")
    List<UserWithPlants> getAllUsersAndPlants();

    @Transaction
    @Query("SELECT * FROM Users WHERE id = :userId")
    List<UserWithPlants> getUserAndPlants(int userId);
}

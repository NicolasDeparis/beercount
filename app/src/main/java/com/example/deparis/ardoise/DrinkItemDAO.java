package com.example.deparis.ardoise;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DrinkItemDAO {

    @Query("SELECT * FROM drinks")
    List<DrinkItem> getAll();

    @Query("SELECT * FROM drinks WHERE name LIKE :name LIMIT 1")
    DrinkItem findByName(String name);

    @Insert
    void insert(DrinkItem drinkItems);

    @Update
    void update(DrinkItem drinkItems);

    @Delete
    void delete(DrinkItem drinkItems);
}
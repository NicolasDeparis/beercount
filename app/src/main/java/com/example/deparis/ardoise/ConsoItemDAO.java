package com.example.deparis.ardoise;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ConsoItemDAO {

    @Query("SELECT * FROM conso")
    List<ConsoItem> getAll();

    @Query("SELECT * FROM conso WHERE name LIKE :name LIMIT 1")
    ConsoItem findByName(String name);

    @Insert
    void insert(ConsoItem consoItems);

    @Update
    void update(ConsoItem consoItems);

    @Delete
    void delete(ConsoItem consoItems);
}
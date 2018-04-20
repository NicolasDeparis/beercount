package com.example.deparis.ardoise;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {DrinkItem.class}, version = 1)// , exportSchema = false
public abstract class DrinkDatabase extends RoomDatabase {


    public abstract DrinkItemDAO drinkDAO();

    private static DrinkDatabase INSTANCE;
    public static DrinkDatabase getDrinkDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context, DrinkDatabase.class, "drinks")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
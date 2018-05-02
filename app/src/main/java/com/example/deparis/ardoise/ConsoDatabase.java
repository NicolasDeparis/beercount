package com.example.deparis.ardoise;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {ConsoItem.class}, version = 1, exportSchema=true)
public abstract class ConsoDatabase extends RoomDatabase {

    public abstract ConsoItemDAO consoDAO();

    private static ConsoDatabase INSTANCE;
    public static ConsoDatabase getConsoDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context, ConsoDatabase.class, "conso")
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
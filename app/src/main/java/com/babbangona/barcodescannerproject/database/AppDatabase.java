package com.babbangona.barcodescannerproject.database;


import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.babbangona.barcodescannerproject.model.hsfTransportT;
import com.babbangona.barcodescannerproject.model.inventoryT;
import com.babbangona.barcodescannerproject.model.msaT;

@Database(entities = {inventoryT.class, hsfTransportT.class, msaT.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "inventoryDatabase";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract inventoryTDao inventoryTDao();

    public abstract hsfTransportTDao hsfTransportTDao();

    public abstract msaTDao msaTDao();

}

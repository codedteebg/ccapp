package com.babbangona.barcodescannerproject.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.babbangona.barcodescannerproject.model.drivers;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface driversDao {

    @Insert(onConflict = REPLACE)
    void insertDriver(drivers drivers);

    @Query("SELECT * FROM drivers")
    List<drivers> getAllDrivers();
}

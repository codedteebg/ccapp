package com.babbangona.barcodescannerproject.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.babbangona.barcodescannerproject.model.inventoryT;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface inventoryTDao {

    @Insert (onConflict = REPLACE)
    void insertTxn(inventoryT inventoryT);

    @Update
    void updateTxn (inventoryT inventoryT);

    @Delete
    void deleteTxn (inventoryT inventoryT);

}

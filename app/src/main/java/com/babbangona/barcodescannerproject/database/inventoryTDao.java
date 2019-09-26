package com.babbangona.barcodescannerproject.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.babbangona.barcodescannerproject.model.inventoryT;

import java.util.ArrayList;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface inventoryTDao {

    @Insert (onConflict = REPLACE)
    long insertTxn(inventoryT inventoryT);

    @Insert (onConflict = REPLACE)
    long[] insertPreTxn(ArrayList<inventoryT> inventoryTS);

    @Update
    void updateTxn (inventoryT inventoryT);

    @Delete
    void deleteTxn (inventoryT inventoryT);

    @Query("SELECT * FROM inventoryT WHERE SyncFlag <> :syncflag")
    inventoryT[] selectUnsynced(int syncflag);

}

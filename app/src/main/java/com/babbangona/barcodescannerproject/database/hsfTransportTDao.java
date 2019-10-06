package com.babbangona.barcodescannerproject.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.babbangona.barcodescannerproject.model.hsfTransportT;
import com.babbangona.barcodescannerproject.model.inventoryT;

@Dao
public interface hsfTransportTDao {

    @Insert
    long insertPayment(hsfTransportT hsfTransportT);

    @Query("SELECT * FROM hsfTransportT WHERE SyncFlag = 0")
    hsfTransportT[] selectUnsynced();

    @Query("UPDATE hsfTransportT SET SyncFlag = 1 WHERE HSFID = :hsfID")
    void updateSyncFlag(String hsfID);
}

package com.babbangona.barcodescannerproject.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.babbangona.barcodescannerproject.model.hsfTransportT;
import com.babbangona.barcodescannerproject.model.inventoryT;

import java.util.List;

@Dao
public interface hsfTransportTDao {

    @Insert
    long insertPayment(hsfTransportT hsfTransportT);

    @Query("SELECT * FROM hsfTransportT WHERE SyncFlag = 0")
    hsfTransportT[] selectUnsynced();

    @Query("UPDATE hsfTransportT SET SyncFlag = :syncFlag WHERE HSFID = :hsfID")
    void updateSyncFlag(String hsfID, int syncFlag);

    @Query("SELECT * FROM hsfTransportT")
    List<hsfTransportT> selectAll();

}

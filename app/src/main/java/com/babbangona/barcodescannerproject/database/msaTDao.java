package com.babbangona.barcodescannerproject.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.babbangona.barcodescannerproject.model.hsfTransportT;
import com.babbangona.barcodescannerproject.model.msaT;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface msaTDao {

    @Insert (onConflict = REPLACE)
    void insertMsa(msaT msaT);

    @Query("SELECT * FROM msaT")
    List<msaT> selectMSAS();

    /*@Query("SELECT * FROM hsfTransportT WHERE SyncFlag = 0")
    hsfTransportT[] selectUnsynced();

    @Query("UPDATE hsfTransportT SET SyncFlag = :syncFlag WHERE HSFID = :hsfID")
    void updateSyncFlag(String hsfID, int syncFlag);*/
}

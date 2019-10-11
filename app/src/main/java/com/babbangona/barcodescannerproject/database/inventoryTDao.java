package com.babbangona.barcodescannerproject.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.babbangona.barcodescannerproject.model.cropSummary;
import com.babbangona.barcodescannerproject.model.dateSummary;
import com.babbangona.barcodescannerproject.model.hsf;
import com.babbangona.barcodescannerproject.model.hsfTransportT;
import com.babbangona.barcodescannerproject.model.inventoryT;
import com.babbangona.barcodescannerproject.model.totalSummary;

import java.util.ArrayList;
import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface inventoryTDao {

    @Insert
    long insertTxn(inventoryT inventoryT);

    @Insert (onConflict = REPLACE)
    long[] insertPreTxn(ArrayList<inventoryT> inventoryTS);

    @Update
    int updateTxn (inventoryT inventoryT);

    @Delete
    void deleteTxn (inventoryT inventoryT);

    @Query("SELECT * FROM inventoryT")
    List<inventoryT> selectAll();

    @Query("SELECT * FROM inventoryT WHERE SyncFlag = 0")
    inventoryT[] selectUnsynced();

    @Query("SELECT * FROM inventoryT WHERE HSFID = :hsfID")
    List<inventoryT> checkHSF(String hsfID);

    @Query("SELECT * FROM inventoryT WHERE HSFID = :hsfID")
    inventoryT retrieveHSF(String hsfID);

    @Query("SELECT SUM(BagsMarketed) as totalBagsCrop, COUNT(HSFID) as txnCrop FROM inventoryT WHERE SeedType = :seedType AND DeletedFlag != 1")
    cropSummary showCropSummary(String seedType);

    @Query("SELECT SUM(BagsMarketed) as totalBagsDate, COUNT(HSFID) as txnDate FROM inventoryT WHERE DateProcessed = :dateP AND DeletedFlag = 0")
    dateSummary showDateSummary(String dateP);

    @Query("SELECT SUM(BagsMarketed) as totalBagsTotal, COUNT(HSFID) as txnTotal FROM inventoryT WHERE DeletedFlag = 0")
    totalSummary showTotalSummary();


    @Query("SELECT HSFID, FieldID, BagsMarketed, BagsRate, TransporterID FROM inventoryT WHERE TransportPaidFlag = 0 and DeletedFlag = 0")
    List<hsf> selectUnpaidTransport();

    @Query("SELECT * FROM inventoryT WHERE DateProcessed = :dateP and DeletedFlag = 0")
    List<inventoryT> selectHSFForDate(String dateP);

    @Query("UPDATE inventoryT SET TransportPaidFlag = 1,SyncFlag = 0 WHERE HSFID = :hsfID")
    void updateTransport(String hsfID);

    @Query("UPDATE inventoryT SET SyncFlag = :syncFlag WHERE HSFID = :hsfID")
    void updateSyncFlag(String hsfID, int syncFlag);

    @Query("UPDATE inventoryT SET DeletedFlag = 1, SyncFlag = 0 WHERE HSFID = :hsfID")
    void deleteHSF(String hsfID);


}

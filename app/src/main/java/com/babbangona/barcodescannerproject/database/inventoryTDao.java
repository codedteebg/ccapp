package com.babbangona.barcodescannerproject.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.babbangona.barcodescannerproject.model.cropSummary;
import com.babbangona.barcodescannerproject.model.dateSummary;
import com.babbangona.barcodescannerproject.model.hsfTransportT;
import com.babbangona.barcodescannerproject.model.inventoryT;
import com.babbangona.barcodescannerproject.model.totalSummary;

import java.util.ArrayList;
import java.util.List;

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

    @Query("SELECT * FROM inventoryT WHERE SyncFlag = 0")
    inventoryT[] selectUnsynced();

    @Query("SELECT SUM(BagsMarketed) as totalBagsCrop, COUNT(HSFID) as txnCrop FROM inventoryT WHERE SeedType = :seedType AND DeletedFlag != 1")
    cropSummary showCropSummary(String seedType);

    @Query("SELECT SUM(BagsMarketed) as totalBagsDate, COUNT(HSFID) as txnDate FROM inventoryT WHERE DateProcessed = :dateP AND DeletedFlag != 1")
    dateSummary showDateSummary(String dateP);

    @Query("SELECT SUM(BagsMarketed) as totalBagsTotal, COUNT(HSFID) as txnTotal FROM inventoryT")
    totalSummary showTotalSummary();

    @Query("SELECT HSFID, FieldID, BagsMarketed, BagsRate, TransporterID, CCOID FROM inventoryT WHERE TransportPaidFlag = 0")
    List<hsfTransportT> selectUnpaidTransport();



}

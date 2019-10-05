package com.babbangona.barcodescannerproject.database;

import androidx.room.Dao;
import androidx.room.Insert;

import com.babbangona.barcodescannerproject.model.hsfTransportT;

@Dao
public interface hsfTransportTDao {

    @Insert
    long insertPayment(hsfTransportT hsfTransportT);
}

package com.babbangona.barcodescannerproject.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "drivers", primaryKeys = "driverData")
public class drivers {


    @NonNull
    @ColumnInfo (name = "driverData")
    private String driverData;


    public drivers(String driverData) {
        this.driverData = driverData;
    }

    @NonNull
    public String getDriverData() {
        return driverData;
    }

    public void setDriverData(@NonNull String driverData) {
        this.driverData = driverData;
    }


}

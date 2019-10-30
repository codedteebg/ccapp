package com.babbangona.barcodescannerproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class driversResponse {

    @SerializedName("driver_data")
    @Expose
    private String driverData;
    @SerializedName("sync_time")
    @Expose
    private String syncTime;

    public String getDriverData() {
        return driverData;
    }

    public void setDriverData(String driverData) {
        this.driverData = driverData;
    }

    public String getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(String syncTime) {
        this.syncTime = syncTime;
    }

}

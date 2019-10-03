package com.babbangona.barcodescannerproject.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "hsfTransportT", primaryKeys = "HSFID")
public class hsfTransportT {
    @ColumnInfo
    @NonNull
    private String HSFID;

    @ColumnInfo
    @NonNull
    private String FieldID;

    @ColumnInfo
    @NonNull
    private int BagsMarketed;

    @ColumnInfo
    @NonNull
    private int BagsRate;

    @ColumnInfo
    @NonNull
    private String TransporterID;

    @ColumnInfo
    @NonNull
    private String CCOID;

    @ColumnInfo
    @NonNull
    private String MSAID;

    @ColumnInfo
    @NonNull
    private int AmountPaid;

    @ColumnInfo
    @NonNull
    private String DatePaid;


    public hsfTransportT(@NonNull String HSFID, @NonNull String fieldID, int bagsMarketed, int bagsRate, @NonNull String transporterID, @NonNull String CCOID, @NonNull String MSAID, int amountPaid, @NonNull String datePaid) {
        this.HSFID = HSFID;
        FieldID = fieldID;
        BagsMarketed = bagsMarketed;
        BagsRate = bagsRate;
        TransporterID = transporterID;
        this.CCOID = CCOID;
        this.MSAID = MSAID;
        AmountPaid = amountPaid;
        DatePaid = datePaid;
    }

    @Ignore
    public hsfTransportT(@NonNull String HSFID, @NonNull String fieldID, int bagsMarketed, int bagsRate, @NonNull String transporterID, @NonNull String CCOID) {
        this.HSFID = HSFID;
        FieldID = fieldID;
        BagsMarketed = bagsMarketed;
        BagsRate = bagsRate;
        TransporterID = transporterID;
        this.CCOID = CCOID;
    }

    @NonNull
    public String getHSFID() {
        return HSFID;
    }

    public void setHSFID(@NonNull String HSFID) {
        this.HSFID = HSFID;
    }

    @NonNull
    public String getFieldID() {
        return FieldID;
    }

    public void setFieldID(@NonNull String fieldID) {
        FieldID = fieldID;
    }

    public int getBagsMarketed() {
        return BagsMarketed;
    }

    public void setBagsMarketed(int bagsMarketed) {
        BagsMarketed = bagsMarketed;
    }

    public int getBagsRate() {
        return BagsRate;
    }

    public void setBagsRate(int bagsRate) {
        BagsRate = bagsRate;
    }

    @NonNull
    public String getTransporterID() {
        return TransporterID;
    }

    public void setTransporterID(@NonNull String transporterID) {
        TransporterID = transporterID;
    }

    @NonNull
    public String getCCOID() {
        return CCOID;
    }

    public void setCCOID(@NonNull String CCOID) {
        this.CCOID = CCOID;
    }

    @NonNull
    public String getMSAID() {
        return MSAID;
    }

    public void setMSAID(@NonNull String MSAID) {
        this.MSAID = MSAID;
    }

    public int getAmountPaid() {
        return AmountPaid;
    }

    public void setAmountPaid(int amountPaid) {
        AmountPaid = amountPaid;
    }

    @NonNull
    public String getDatePaid() {
        return DatePaid;
    }

    public void setDatePaid(@NonNull String datePaid) {
        DatePaid = datePaid;
    }


}

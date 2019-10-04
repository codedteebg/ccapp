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
    private String MSAID;

    @ColumnInfo
    private int AmountPaid;

    @ColumnInfo
    private String DatePaid;


    public hsfTransportT(@NonNull String HSFID, @NonNull String FieldID, int BagsMarketed, int BagsRate, @NonNull String TransporterID, @NonNull String CCOID, String MSAID, int AmountPaid, String DatePaid) {
        this.HSFID = HSFID;
        this.FieldID = FieldID;
        this.BagsMarketed = BagsMarketed;
        this.BagsRate = BagsRate;
        this.TransporterID = TransporterID;
        this.CCOID = CCOID;
        this.MSAID = MSAID;
        this.AmountPaid = AmountPaid;
        this.DatePaid = DatePaid;
    }

 /*   @Ignore
    public hsfTransportT(@NonNull String HSFID, @NonNull String fieldID, int bagsMarketed, int bagsRate, @NonNull String transporterID, @NonNull String CCOID) {
        this.HSFID = HSFID;
        FieldID = fieldID;
        BagsMarketed = bagsMarketed;
        BagsRate = bagsRate;
        TransporterID = transporterID;
        this.CCOID = CCOID;
    }*/

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

    public void setFieldID(@NonNull String FieldID) {
        this.FieldID = FieldID;
    }

    public int getBagsMarketed() {
        return BagsMarketed;
    }

    public void setBagsMarketed(int BagsMarketed) {
        this.BagsMarketed = BagsMarketed;
    }

    public int getBagsRate() {
        return BagsRate;
    }

    public void setBagsRate(int BagsRate) {
        this.BagsRate = BagsRate;
    }

    @NonNull
    public String getTransporterID() {
        return TransporterID;
    }

    public void setTransporterID(@NonNull String TransporterID) {
        this.TransporterID = TransporterID;
    }

    @NonNull
    public String getCCOID() {
        return CCOID;
    }

    public void setCCOID(@NonNull String CCOID) {
        this.CCOID = CCOID;
    }


    public String getMSAID() {
        return MSAID;
    }

    public void setMSAID( String MSAID) {
        this.MSAID = MSAID;
    }

    public int getAmountPaid() {
        return AmountPaid;
    }

    public void setAmountPaid(int AmountPaid) {
        this.AmountPaid = AmountPaid;
    }


    public String getDatePaid() {
        return DatePaid;
    }

    public void setDatePaid( String DatePaid) {
        this.DatePaid = DatePaid;
    }


}

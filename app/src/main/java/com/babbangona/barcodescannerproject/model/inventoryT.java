package com.babbangona.barcodescannerproject.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity (tableName = "inventoryT", primaryKeys = "HSFID")
public class inventoryT {

    @ColumnInfo (name = "HSFID")
    @NonNull
    private String HSFID;

    @ColumnInfo (name = "Unique_Field_ID")
    @NonNull
    private String Unique_Field_ID;

    @ColumnInfo (name = "FieldID")
    @NonNull
    private String FieldID;

    @ColumnInfo (name = "BagsMarketed")
    @NonNull
    private int BagsMarketed;

    @ColumnInfo (name = "KGMarketed")
    @NonNull
    private int KGMarketed;

    @ColumnInfo (name = "SeedType")
    @NonNull
    private String SeedType;

    @ColumnInfo(name = "DateProcessed")
    @NonNull
    private String DateProcessed;

    @ColumnInfo (name = "MoldCount")
    @NonNull
    private int MoldCount;

    @ColumnInfo (name = "PercentClean")
    @NonNull
    private float PercentClean;

    @ColumnInfo (name = "PercentMoisture")
    @NonNull
    private float PercentMoisture;

    @ColumnInfo (name = "SyncFlag")
    @NonNull
    private int SyncFlag;



    public inventoryT (@NonNull String HSFID, String Unique_Field_ID, String FieldID, int BagsMarketed, int KGMarketed,
                       String SeedType, String DateProcessed, int MoldCount, int PercentClean, int PercentMoisture){

        this.HSFID = HSFID;
        this.Unique_Field_ID = Unique_Field_ID;
        this.FieldID = FieldID;
        this.BagsMarketed = BagsMarketed;
        this.KGMarketed = KGMarketed;
        this.SeedType = SeedType;
        this.DateProcessed = DateProcessed;
        this.MoldCount = MoldCount;
        this.PercentClean = (float)PercentClean/100;
        this.PercentMoisture = (float)PercentMoisture/100;
        this.SyncFlag = 0;
    }

    @NonNull
    public String getHSFID() {
        return HSFID;
    }

    public void setHSFID(@NonNull String HSFID) {
        this.HSFID = HSFID;
    }

    @NonNull
    public String getUnique_Field_ID() {
        return Unique_Field_ID;
    }

    public void setUnique_Field_ID(@NonNull String unique_Field_ID) {
        Unique_Field_ID = unique_Field_ID;
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

    public int getKGMarketed() {
        return KGMarketed;
    }

    public void setKGMarketed(int KGMarketed) {
        this.KGMarketed = KGMarketed;
    }

    @NonNull
    public String getSeedType() {
        return SeedType;
    }

    public void setSeedType(@NonNull String seedType) {
        SeedType = seedType;
    }

    @NonNull
    public String getDateProcessed() {
        return DateProcessed;
    }

    public void setDateProcessed(@NonNull String dateProcessed) {
        DateProcessed = dateProcessed;
    }

    public int getMoldCount() {
        return MoldCount;
    }

    public void setMoldCount(int moldCount) {
        MoldCount = moldCount;
    }

    public float getPercentClean() {
        return (int)(PercentClean * 100);
    }

    public void setPercentClean(float percentClean) {
        PercentClean = percentClean / 100;
    }

    public float getPercentMoisture() {
        return (int)(PercentMoisture * 100);
    }

    public void setPercentMoisture(float percentMoisture) {
        PercentMoisture = percentMoisture / 100;
    }

    public int getSyncFlag() {
        return SyncFlag;
    }

    public void  setSyncFlag(int SyncFlag){
        this.SyncFlag = SyncFlag;
    }
}

package com.babbangona.barcodescannerproject.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity (tableName = "inventoryT", primaryKeys = "HSFID")
public class inventoryT {

    @ColumnInfo (name = "HSFID")
    @NonNull
    private String HSFID;

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
    private int PercentClean;

    @ColumnInfo (name = "PercentMoisture")
    @NonNull
    private int PercentMoisture;

    @ColumnInfo (name = "WarehouseID")
    @NonNull
    private String WarehouseID;

    @ColumnInfo (name = "CCOID")
    @NonNull
    private String CCOID;

    @ColumnInfo (name = "TransporterID")
    @NonNull
    private String TransporterID;

    @ColumnInfo (name = "TransportPaidFlag")
    @NonNull
    private int TransportPaidFlag;

    @ColumnInfo (name = "TransporterRating")
    @NonNull
    private String TransporterRating;

    @ColumnInfo (name = "BagsRate")
    @NonNull
    private int BagsRate;

    @ColumnInfo (name = "SyncFlag")
    @NonNull
    private int SyncFlag;

    public inventoryT (@NonNull String HSFID, String FieldID, int BagsMarketed, int KGMarketed,
                       String SeedType, String DateProcessed, int MoldCount, int PercentClean, int PercentMoisture,
                       String WarehouseID, String CCOID, String TransporterID, int TransportPaidFlag,String TransporterRating, int BagsRate, int SyncFlag){

        this.HSFID = HSFID;
        this.FieldID = FieldID;
        this.BagsMarketed = BagsMarketed;
        this.KGMarketed = KGMarketed;
        this.SeedType = SeedType;
        this.DateProcessed = DateProcessed;
        this.MoldCount = MoldCount;
        /*this.PercentClean = (float)PercentClean/100;
        this.PercentMoisture = (float)PercentMoisture/100;*/
        this.PercentClean = PercentClean;
        this.PercentMoisture = PercentMoisture;
        this.WarehouseID = WarehouseID;
        this.CCOID = CCOID;
        this.TransporterID = TransporterID;
        this.TransportPaidFlag = TransportPaidFlag;
        this.TransporterRating = TransporterRating;
        this.BagsRate = BagsRate;
        this.SyncFlag = SyncFlag;
    }

    public inventoryT (@NonNull String HSFID, String FieldID, int BagsMarketed, int KGMarketed,
                       String SeedType, String DateProcessed, int MoldCount, int PercentClean, int PercentMoisture,
                       String WarehouseID, String CCOID, String TransporterID, String TransporterRating){

        this.HSFID = HSFID;
        this.FieldID = FieldID;
        this.BagsMarketed = BagsMarketed;
        this.KGMarketed = KGMarketed;
        this.SeedType = SeedType;
        this.DateProcessed = DateProcessed;
        this.MoldCount = MoldCount;
        /*this.PercentClean = (float)PercentClean/100;
        this.PercentMoisture = (float)PercentMoisture/100;*/
        this.PercentClean = PercentClean;
        this.PercentMoisture = PercentMoisture;
        this.WarehouseID = WarehouseID;
        this.CCOID = CCOID;
        this.TransporterID = TransporterID;
        this.TransportPaidFlag = 0;
        this.TransporterRating = TransporterRating;
        this.BagsRate = 120;
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

    public int getPercentClean() {
        return (int)(PercentClean * 100);
    }

    public void setPercentClean(int percentClean) {
        PercentClean = percentClean / 100;
    }

    public int getPercentMoisture() {
        return (int)(PercentMoisture * 100);
    }

    public void setPercentMoisture(int percentMoisture) {
        PercentMoisture = percentMoisture / 100;
    }

    @NonNull
    public String getWarehouseID() {
        return WarehouseID;
    }

    public void setWarehouseID(@NonNull String warehouseID) {
        WarehouseID = warehouseID;
    }

    @NonNull
    public String getCCOID() {
        return CCOID;
    }

    public void setCCOID(@NonNull String CCOID) {
        this.CCOID = CCOID;
    }

    @NonNull
    public String getTransporterID() {
        return TransporterID;
    }

    public void setTransporterID(@NonNull String transporterID) {
        TransporterID = transporterID;
    }

    public int getTransportPaidFlag() {
        return TransportPaidFlag;
    }

    public void setTransportPaidFlag(int transportPaidFlag) {
        TransportPaidFlag = transportPaidFlag;
    }

    public int getBagsRate() {
        return BagsRate;
    }

    public void setBagsRate(int bagsRate) {
        BagsRate = bagsRate;
    }

    public int getSyncFlag() {
        return SyncFlag;
    }

    public void setSyncFlag(int syncFlag) {
        SyncFlag = syncFlag;
    }

    @NonNull
    public String getTransporterRating() {
        return TransporterRating;
    }

    public void setTransporterRating(@NonNull String transporterRating) {
        TransporterRating = transporterRating;
    }


}

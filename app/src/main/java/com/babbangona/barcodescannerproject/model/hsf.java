package com.babbangona.barcodescannerproject.model;

public class hsf {
    private String HSFID;
    private String FieldID;
    private int BagsMarketed;
    private int BagsRate;
    private String TransporterID;

    public hsf(String HSFID, String FieldID, int BagsMarketed, int BagsRate, String TransporterID) {
        this.HSFID = HSFID;
        this.FieldID = FieldID;
        this.BagsMarketed = BagsMarketed;
        this.BagsRate = BagsRate;
        this.TransporterID = TransporterID;
    }

    public String getHSFID() {
        return HSFID;
    }

    public void setHSFID(String HSFID) {
        this.HSFID = HSFID;
    }

    public String getFieldID() {
        return FieldID;
    }

    public void setFieldID(String FieldID) {
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

    public String getTransporterID() {
        return TransporterID;
    }

    public void setTransporterID(String TransporterID) {
        this.TransporterID = TransporterID;
    }



}

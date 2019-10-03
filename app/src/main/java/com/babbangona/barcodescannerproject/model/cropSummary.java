package com.babbangona.barcodescannerproject.model;

public class cropSummary {
    private int totalBagsCrop;
    private int txnCrop;

    public cropSummary(int totalBagsCrop, int txnCrop){
        this.totalBagsCrop = totalBagsCrop;
        this.txnCrop = txnCrop;
    }

    public int getTotalBagsCrop() {
        return totalBagsCrop;
    }

    public void setTotalBagsCrop(int totalBagsCrop) {
        this.totalBagsCrop = totalBagsCrop;
    }

    public int getTxnCrop() {
        return txnCrop;
    }

    public void setTxnCrop(int txnCrop) {
        this.txnCrop = txnCrop;
    }

    public String toString(){
        return getTotalBagsCrop()+""+getTxnCrop()+"";
    }


}

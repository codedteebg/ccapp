package com.babbangona.barcodescannerproject.model;

public class dateSummary {
    private int totalBagsDate;
    private int txnDate;


    public dateSummary(int totalBagsDate, int txnDate) {
        this.totalBagsDate = totalBagsDate;
        this.txnDate = txnDate;
    }

    public int getTotalBagsDate() {
        return totalBagsDate;
    }

    public void setTotalBagsDate(int totalBagsDate) {
        this.totalBagsDate = totalBagsDate;
    }

    public int getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(int txnDate) {
        this.txnDate = txnDate;
    }

    public String toString(){
        return getTotalBagsDate()+""+getTxnDate()+"";
    }


}

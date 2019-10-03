package com.babbangona.barcodescannerproject.model;

public class totalSummary {
    private int totalBagsTotal;
    private int txnTotal;

    public totalSummary(int totalBagsTotal, int txnTotal) {
        this.totalBagsTotal = totalBagsTotal;
        this.txnTotal = txnTotal;
    }

    public int getTotalBagsTotal() {
        return totalBagsTotal;
    }

    public void setTotalBagsTotal(int totalBagsTotal) {
        this.totalBagsTotal = totalBagsTotal;
    }

    public int getTxnTotal() {
        return txnTotal;
    }

    public void setTxnTotal(int txnTotal) {
        this.txnTotal = txnTotal;
    }

    public String toString(){
        return getTotalBagsTotal()+""+getTxnTotal()+"";
    }

}

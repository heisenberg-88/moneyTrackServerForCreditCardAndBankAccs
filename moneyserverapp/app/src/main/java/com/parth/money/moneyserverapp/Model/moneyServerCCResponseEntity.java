package com.parth.money.moneyserverapp.Model;

import java.math.BigDecimal;

public class moneyServerCCResponseEntity {

    private String txnId;

    private String txnBillingMonth;

    private String txnBillingYear;

    private String txnCCused;

    private String txnDetails;

    private BigDecimal txnAmount;

    private Boolean txnIsEmi;

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getTxnBillingMonth() {
        return txnBillingMonth;
    }

    public void setTxnBillingMonth(String txnBillingMonth) {
        this.txnBillingMonth = txnBillingMonth;
    }

    public String getTxnBillingYear() {
        return txnBillingYear;
    }

    public void setTxnBillingYear(String txnBillingYear) {
        this.txnBillingYear = txnBillingYear;
    }

    public String getTxnCCused() {
        return txnCCused;
    }

    public void setTxnCCused(String txnCCused) {
        this.txnCCused = txnCCused;
    }

    public String getTxnDetails() {
        return txnDetails;
    }

    public void setTxnDetails(String txnDetails) {
        this.txnDetails = txnDetails;
    }

    public BigDecimal getTxnAmount() {
        return txnAmount;
    }

    public void setTxnAmount(BigDecimal txnAmount) {
        this.txnAmount = txnAmount;
    }

    public Boolean getTxnIsEmi() {
        return txnIsEmi;
    }

    public void setTxnIsEmi(Boolean txnIsEmi) {
        this.txnIsEmi = txnIsEmi;
    }
}

package com.parth.money.moneyserverapp.Model;

import java.math.BigDecimal;

public class moneyServerCCTopTxnResponseEntity {

    private String txnId;

    private String txnDetails;

    private BigDecimal txnAmount;

    private String ccorbank;


    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
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

    public String getCcorbank() {
        return ccorbank;
    }

    public void setCcorbank(String ccorbank) {
        this.ccorbank = ccorbank;
    }
}

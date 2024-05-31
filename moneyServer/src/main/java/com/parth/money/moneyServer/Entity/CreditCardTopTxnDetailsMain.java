package com.parth.money.moneyServer.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name="CREDIT_CARD_TOP_TXNS_DETAILS_MAIN")
public class CreditCardTopTxnDetailsMain implements Serializable {

    @Id
    @Column(name="txn_id")
    private String txnId;

    @Column(name="txn_details")
    private String txnDetails;

    @Column(name="txn_amount")
    private BigDecimal txnAmount;

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


}

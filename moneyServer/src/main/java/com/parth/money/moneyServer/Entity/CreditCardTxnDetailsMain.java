package com.parth.money.moneyServer.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name="CREDIT_CARD_TXNS_DETAILS_MAIN")
public class CreditCardTxnDetailsMain implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name="txn_id")
    private String txnId;

    @Column(name="txn_billing_month")
    private String txnBillingMonth;

    @Column(name="txn_billing_year")
    private String txnBillingYear;

    @Column(name="txn_cc_used")
    private String txnCCused;

    @Column(name="txn_details")
    private String txnDetails;

    @Column(name="txn_amount")
    private BigDecimal txnAmount;

    @Column(name="txn_isemi")
    private Boolean txnIsEmi;

    @Column(name="txn_emi_id")
    private String txnEmiId;

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

    public int getTxnBillingYearINTEGER(){
        return Integer.parseInt(txnBillingYear);
    }

    public int getTxnBillingMonthINTEGER(){
        List<String> months = Arrays.asList(
                "January", "February", "March", "April",
                "May", "June", "July", "August",
                "September", "October", "November", "December"
        );
        return 1 + months.indexOf(txnBillingMonth);
    }

    public String getTxnEmiId() {
        return txnEmiId;
    }

    public void setTxnEmiId(String txnEmiId) {
        this.txnEmiId = txnEmiId;
    }
}

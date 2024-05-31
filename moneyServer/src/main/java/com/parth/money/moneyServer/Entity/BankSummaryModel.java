package com.parth.money.moneyServer.Entity;

import java.math.BigDecimal;

public class BankSummaryModel {

    private String month;
    private String year;
    private BigDecimal Bank_Of_Maharashtra_OLD;
    private BigDecimal HDFC_Salary_Account;
    private BigDecimal Amount_Total;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public BigDecimal getBank_Of_Maharashtra_OLD() {
        return Bank_Of_Maharashtra_OLD;
    }

    public void setBank_Of_Maharashtra_OLD(BigDecimal bank_Of_Maharashtra_OLD) {
        Bank_Of_Maharashtra_OLD = bank_Of_Maharashtra_OLD;
    }

    public BigDecimal getHDFC_Salary_Account() {
        return HDFC_Salary_Account;
    }

    public void setHDFC_Salary_Account(BigDecimal HDFC_Salary_Account) {
        this.HDFC_Salary_Account = HDFC_Salary_Account;
    }

    public BigDecimal getAmount_Total() {
        return Amount_Total;
    }

    public void setAmount_Total(BigDecimal amount_Total) {
        Amount_Total = amount_Total;
    }
}

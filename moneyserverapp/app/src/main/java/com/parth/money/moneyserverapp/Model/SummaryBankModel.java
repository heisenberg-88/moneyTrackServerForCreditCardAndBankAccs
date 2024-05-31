package com.parth.money.moneyserverapp.Model;

import java.math.BigDecimal;

public class SummaryBankModel {

    private String month;
    private String year;
    private BigDecimal bank_Of_Maharashtra_OLD;
    private BigDecimal hdfc_Salary_Account;
    private BigDecimal amount_Total;

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
        return bank_Of_Maharashtra_OLD;
    }

    public void setBank_Of_Maharashtra_OLD(BigDecimal bank_Of_Maharashtra_OLD) {
        this.bank_Of_Maharashtra_OLD = bank_Of_Maharashtra_OLD;
    }

    public BigDecimal getHdfc_Salary_Account() {
        return hdfc_Salary_Account;
    }

    public void setHdfc_Salary_Account(BigDecimal hdfc_Salary_Account) {
        this.hdfc_Salary_Account = hdfc_Salary_Account;
    }

    public BigDecimal getAmount_Total() {
        return amount_Total;
    }

    public void setAmount_Total(BigDecimal amount_Total) {
        this.amount_Total = amount_Total;
    }
}

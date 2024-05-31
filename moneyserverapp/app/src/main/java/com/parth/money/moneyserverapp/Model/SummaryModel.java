package com.parth.money.moneyserverapp.Model;

import java.math.BigDecimal;

public class SummaryModel {
    private String month;
    private String year;
    private BigDecimal standard_Chartered_Ultimate_Total;
    private BigDecimal hdfc_Regalia_Gold_Total;
    private BigDecimal amazonPay_icici_Total;
    private BigDecimal amazon_PayLater_Total;
    private BigDecimal flipkart_PayLater_Total;
    private BigDecimal amount_Total;

    public BigDecimal getStandard_Chartered_Ultimate_Total() {
        return standard_Chartered_Ultimate_Total;
    }

    public void setStandard_Chartered_Ultimate_Total(BigDecimal standard_Chartered_Ultimate_Total) {
        this.standard_Chartered_Ultimate_Total = standard_Chartered_Ultimate_Total;
    }

    public BigDecimal getHdfc_Regalia_Gold_Total() {
        return hdfc_Regalia_Gold_Total;
    }

    public void setHdfc_Regalia_Gold_Total(BigDecimal hdfc_Regalia_Gold_Total) {
        this.hdfc_Regalia_Gold_Total = hdfc_Regalia_Gold_Total;
    }

    public BigDecimal getAmazonPay_icici_Total() {
        return amazonPay_icici_Total;
    }

    public void setAmazonPay_icici_Total(BigDecimal amazonPay_icici_Total) {
        this.amazonPay_icici_Total = amazonPay_icici_Total;
    }

    public BigDecimal getAmazon_PayLater_Total() {
        return amazon_PayLater_Total;
    }

    public void setAmazon_PayLater_Total(BigDecimal amazon_PayLater_Total) {
        this.amazon_PayLater_Total = amazon_PayLater_Total;
    }

    public BigDecimal getFlipkart_PayLater_Total() {
        return flipkart_PayLater_Total;
    }

    public void setFlipkart_PayLater_Total(BigDecimal flipkart_PayLater_Total) {
        this.flipkart_PayLater_Total = flipkart_PayLater_Total;
    }

    public BigDecimal getAmount_Total() {
        return amount_Total;
    }

    public void setAmount_Total(BigDecimal amount_Total) {
        this.amount_Total = amount_Total;
    }

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
}

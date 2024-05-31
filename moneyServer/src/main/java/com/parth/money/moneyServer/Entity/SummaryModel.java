package com.parth.money.moneyServer.Entity;

import java.math.BigDecimal;

public class SummaryModel {
    private String month;
    private String year;
    private BigDecimal Standard_Chartered_Ultimate_Total;
    private BigDecimal HDFC_Regalia_Gold_Total;
    private BigDecimal AmazonPay_icici_Total;
    private BigDecimal Amazon_PayLater_Total;
    private BigDecimal Flipkart_PayLater_Total;
    private BigDecimal Amount_Total;

    public BigDecimal getStandard_Chartered_Ultimate_Total() {
        return Standard_Chartered_Ultimate_Total;
    }

    public void setStandard_Chartered_Ultimate_Total(BigDecimal standard_Chartered_Ultimate_Total) {
        Standard_Chartered_Ultimate_Total = standard_Chartered_Ultimate_Total;
    }

    public BigDecimal getHDFC_Regalia_Gold_Total() {
        return HDFC_Regalia_Gold_Total;
    }

    public void setHDFC_Regalia_Gold_Total(BigDecimal HDFC_Regalia_Gold_Total) {
        this.HDFC_Regalia_Gold_Total = HDFC_Regalia_Gold_Total;
    }

    public BigDecimal getAmazonPay_icici_Total() {
        return AmazonPay_icici_Total;
    }

    public void setAmazonPay_icici_Total(BigDecimal amazonPay_icici_Total) {
        AmazonPay_icici_Total = amazonPay_icici_Total;
    }

    public BigDecimal getAmazon_PayLater_Total() {
        return Amazon_PayLater_Total;
    }

    public void setAmazon_PayLater_Total(BigDecimal amazon_PayLater_Total) {
        Amazon_PayLater_Total = amazon_PayLater_Total;
    }

    public BigDecimal getFlipkart_PayLater_Total() {
        return Flipkart_PayLater_Total;
    }

    public void setFlipkart_PayLater_Total(BigDecimal flipkart_PayLater_Total) {
        Flipkart_PayLater_Total = flipkart_PayLater_Total;
    }

    public BigDecimal getAmount_Total() {
        return Amount_Total;
    }

    public void setAmount_Total(BigDecimal amount_Total) {
        Amount_Total = amount_Total;
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

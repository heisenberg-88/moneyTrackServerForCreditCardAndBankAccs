package com.parth.money.moneyServer.Utils;

import com.parth.money.moneyServer.Entity.BankSummaryModel;
import com.parth.money.moneyServer.Entity.BankTxnDetailsMain;
import com.parth.money.moneyServer.Entity.CreditCardTxnDetailsMain;
import com.parth.money.moneyServer.Repository.BankTxnDetailsMainRepository;
import com.parth.money.moneyServer.Repository.MoneyServerPropertiesDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class BankSummaryUtility {

    @Autowired
    BankTxnDetailsMainRepository bankTxnDetailsMainRepository;

    @Autowired
    MoneyServerPropertiesDataRepository moneyServerPropertiesDataRepository;

    List<String> months = Arrays.asList(
            "January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December"
    );

    public List<BankSummaryModel> getSummarylist(){
        int startMonthinDB = months.indexOf("January");
        String startYearinDB = "2023";
        List<BankSummaryModel> returnListData = new ArrayList<>();

        String stopperMonthinDB = moneyServerPropertiesDataRepository.findById("banklastUsedmonth").getValue();
        String stopperYearinDB = moneyServerPropertiesDataRepository.findById("banklastUsedyear").getValue();
        while(true){
            if(stopperMonthinDB.equalsIgnoreCase(months.get(startMonthinDB%12))
                    && stopperYearinDB.equalsIgnoreCase(startYearinDB)){
                returnListData.add(getSummaryFromTxns(months.get(startMonthinDB%12),startYearinDB));
                break;
            }
            returnListData.add(getSummaryFromTxns(months.get(startMonthinDB%12),startYearinDB));
            if(startMonthinDB%12==11){
                startYearinDB = String.valueOf(Integer.parseInt(startYearinDB)+1);
            }
            startMonthinDB+=1;
        }
        returnListData.add(getSummaryFromTxns("X","X"));

        return returnListData;
    }

    public BankSummaryModel getSummaryFromTxns(String month, String year){
        BankSummaryModel returnModel = new BankSummaryModel();
        BigDecimal bankOfMaharashtra = new BigDecimal(0);
        BigDecimal hdfcBank = new BigDecimal(0);
        BigDecimal totalAmt = new BigDecimal(0);

        List<BankTxnDetailsMain> txnList;

        if("X".equalsIgnoreCase(month) && "X".equalsIgnoreCase(year)){
            txnList = bankTxnDetailsMainRepository.findAll();
            returnModel.setYear("COMPLETE");
            returnModel.setMonth("COMPLETE");
        }else{
            txnList = bankTxnDetailsMainRepository.findByBanktxnBillingMonthAndBanktxnBillingYear(month,year);
            returnModel.setYear(year);
            returnModel.setMonth(month);
        }
        for(BankTxnDetailsMain banktxn:txnList){
            if(banktxn.getBankAccName().startsWith("HDFC Bank")){
                hdfcBank = hdfcBank.add(banktxn.getBanktxnAmount());
            }
            if(banktxn.getBankAccName().startsWith("Bank Of Maharashtra")){
                bankOfMaharashtra = bankOfMaharashtra.add(banktxn.getBanktxnAmount());
            }
        }

        totalAmt = totalAmt.add(hdfcBank);
        totalAmt = totalAmt.add(bankOfMaharashtra);

        returnModel.setBank_Of_Maharashtra_OLD(bankOfMaharashtra);
        returnModel.setHDFC_Salary_Account(hdfcBank);
        returnModel.setAmount_Total(totalAmt);

        return returnModel;
    }

}

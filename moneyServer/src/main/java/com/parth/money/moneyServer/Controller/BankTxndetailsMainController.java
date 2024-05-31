package com.parth.money.moneyServer.Controller;

import com.parth.money.moneyServer.Entity.*;
import com.parth.money.moneyServer.Repository.BankTxnDetailsMainRepository;
import com.parth.money.moneyServer.Repository.CreditCardTopTxnDetailsMainRepository;
import com.parth.money.moneyServer.Repository.MoneyServerPropertiesDataRepository;
import com.parth.money.moneyServer.Utils.BankSummaryUtility;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/moneyServer/BankTxnDetails")
public class BankTxndetailsMainController {

    @Autowired
    BankTxnDetailsMainRepository bankTxnDetailsMainRepository;

    @Autowired
    BankSummaryUtility bankSummaryUtility;

    @Autowired
    MoneyServerPropertiesDataRepository moneyServerPropertiesDataRepository;

    @Autowired
    CreditCardTopTxnDetailsMainRepository creditCardTopTxnDetailsMainRepository;

    @Autowired
    EntityManager entityManager;

    List<String> months = Arrays.asList(
            "January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December"
    );


    //GET txns
    @GetMapping("/txn/{id}")
    public BankTxnDetailsMain getTxnbyId(@PathVariable String id){
        return bankTxnDetailsMainRepository.findByBanktxnId(id);
    }
    @GetMapping("/txn/allTxns")
    public List<BankTxnDetailsMain> getAll(){
        List<BankTxnDetailsMain> returnList =  bankTxnDetailsMainRepository.findAll();
        Collections.sort(returnList, Comparator.comparing(BankTxnDetailsMain::getBankTxnBillingYearINTEGER,Comparator.reverseOrder())
                .thenComparing(BankTxnDetailsMain::getBankTxnBillingMonthINTEGER,Comparator.reverseOrder()).thenComparing(BankTxnDetailsMain::getBanktxnBillingDateINTEGER,Comparator.reverseOrder()).thenComparing(BankTxnDetailsMain::getBankTxnSeqNumOrder,Comparator.reverseOrder()));
        return returnList;
    }

    @GetMapping("/txn")
    public List<BankTxnDetailsMain> getTxnbyMonthAndYear(@RequestParam String month, @RequestParam String year){
        List<BankTxnDetailsMain> returnList = bankTxnDetailsMainRepository.findByBanktxnBillingMonthAndBanktxnBillingYear(month,year);
        Collections.sort(returnList, Comparator.comparing(BankTxnDetailsMain::getBankTxnBillingYearINTEGER,Comparator.reverseOrder())
                .thenComparing(BankTxnDetailsMain::getBankTxnBillingMonthINTEGER,Comparator.reverseOrder()).thenComparing(BankTxnDetailsMain::getBanktxnBillingDateINTEGER,Comparator.reverseOrder()).thenComparing(BankTxnDetailsMain::getBankTxnSeqNumOrder,Comparator.reverseOrder()));
        return returnList;
    }

    @GetMapping("/txn/Summary")
    public BankSummaryModel getSummaryByMonthAndYear(@RequestParam(value="month",required=false,defaultValue="X")String month, @RequestParam(value="year",required=false,defaultValue="X")String year){
        BankSummaryModel summaryModel = bankSummaryUtility.getSummaryFromTxns(month,year);
        return summaryModel;
    }

    @GetMapping("/txn/Summary/allTxns")
    public List<BankSummaryModel> getSummaryAlltxns(){
        return bankSummaryUtility.getSummarylist();
    }

    @GetMapping("/txn/avlBal")
    public BigDecimal getAvlBalFromAccName(@RequestParam String bankAccName){
        String avlBal = moneyServerPropertiesDataRepository.findById(bankAccName).getValue();
        return new BigDecimal(avlBal);
    }



    // POST txns
    @Transactional
    @PostMapping("/txn")
    public BankTxnDetailsMain addNewTxn(@RequestBody BankTxnDetailsMain entity){

        String currDBlastusedYear = moneyServerPropertiesDataRepository.findById("banklastUsedyear").getValue();
        String currDBlastusedMonth = moneyServerPropertiesDataRepository.findById("banklastUsedmonth").getValue();

        if(entity.getBankTxnBillingYearINTEGER()==Integer.parseInt(currDBlastusedYear)){
            if((entity.getBankTxnBillingMonthINTEGER()-1)<months.indexOf(currDBlastusedMonth)){
                return null;
            }
        } else if (entity.getBankTxnBillingYearINTEGER()<Integer.parseInt(currDBlastusedYear)) {
            return null;
        }
        Query query = entityManager.createNamedQuery("MoneyServerPropertiesData.updateLastUsedMonthYear");
        if(!currDBlastusedMonth.equalsIgnoreCase(entity.getBanktxnBillingMonth())){
            query.setParameter("newData",entity.getBanktxnBillingMonth());
            query.setParameter("id","banklastUsedmonth");
            query.executeUpdate();
        }
        if(!currDBlastusedYear.equalsIgnoreCase(entity.getBanktxnBillingYear())){
            query.setParameter("newData",entity.getBanktxnBillingYear());
            query.setParameter("id","banklastUsedyear");
            query.executeUpdate();
        }
        BankTxnDetailsMain returnEntity =  bankTxnDetailsMainRepository.save(entity);
        CreditCardTopTxnDetailsMain topTxnEntity = new CreditCardTopTxnDetailsMain();
        topTxnEntity.setTxnId(returnEntity.getBanktxnId());
        topTxnEntity.setTxnDetails(returnEntity.getBanktxnDetails());
        topTxnEntity.setTxnAmount(returnEntity.getBanktxnAmount().negate());
        creditCardTopTxnDetailsMainRepository.save(topTxnEntity);

        String bankAccName = "avlBal-HDFC";
        if(entity.getBankAccName().startsWith("HDFC Bank")){
            bankAccName = "avlBal-HDFC";
        }
        if(entity.getBankAccName().startsWith("Bank Of Maharashtra")){
            bankAccName = "avlBal-BOM";
        }
        String avlBaltemp = moneyServerPropertiesDataRepository.findById(bankAccName).getValue();
        BigDecimal floatAvlBaltemp = new BigDecimal(avlBaltemp);
        BigDecimal txnAmtcurrent = entity.getBanktxnAmount();
        if(txnAmtcurrent.signum()==-1){
            floatAvlBaltemp = floatAvlBaltemp.add(txnAmtcurrent);
        }else{
            floatAvlBaltemp = floatAvlBaltemp.add(txnAmtcurrent);
        }
        Query queryAvlBalupdate = entityManager.createNamedQuery("MoneyServerPropertiesData.updateAvlBalBybankAccName");
        queryAvlBalupdate.setParameter("id",bankAccName);
        queryAvlBalupdate.setParameter("newData",floatAvlBaltemp.toString());
        queryAvlBalupdate.executeUpdate();

        return returnEntity;
    }


    //DELETE txns
    @Transactional
    @DeleteMapping("/txn/txnDeleteNormal/{id}")
    public String txnDeleteNormal(@PathVariable String id){
        BankTxnDetailsMain responseFromID = bankTxnDetailsMainRepository.findByBanktxnId(id);
        String currDBlastusedYear = moneyServerPropertiesDataRepository.findById("banklastUsedyear").getValue();
        String currDBlastusedMonth = moneyServerPropertiesDataRepository.findById("banklastUsedmonth").getValue();
        if(responseFromID.getBankTxnBillingYearINTEGER()!=Integer.parseInt(currDBlastusedYear)){
            return "DELETE_ERROR";
        }else{
            if((responseFromID.getBankTxnBillingMonthINTEGER()-1)<months.indexOf(currDBlastusedMonth)){
                return "DELETE_ERROR";
            }
        }
        bankTxnDetailsMainRepository.deleteByBankTxnid(id);
        creditCardTopTxnDetailsMainRepository.deleteTopTxnByCCid(id);

        String bankAccName = "avlBal-HDFC";
        if(responseFromID.getBankAccName().startsWith("HDFC Bank")){
            bankAccName = "avlBal-HDFC";
        }
        if(responseFromID.getBankAccName().startsWith("Bank Of Maharashtra")){
            bankAccName = "avlBal-BOM";
        }
        String avlBaltemp = moneyServerPropertiesDataRepository.findById(bankAccName).getValue();
        BigDecimal floatAvlBaltemp = new BigDecimal(avlBaltemp);
        BigDecimal txnAmtcurrent = responseFromID.getBanktxnAmount();
        if(txnAmtcurrent.signum()==-1){
            floatAvlBaltemp = floatAvlBaltemp.subtract(txnAmtcurrent);
        }else{
            floatAvlBaltemp = floatAvlBaltemp.subtract(txnAmtcurrent);
        }
        Query queryAvlBalupdate = entityManager.createNamedQuery("MoneyServerPropertiesData.updateAvlBalBybankAccName");
        queryAvlBalupdate.setParameter("id",bankAccName);
        queryAvlBalupdate.setParameter("newData",floatAvlBaltemp.toString());
        queryAvlBalupdate.executeUpdate();

        return "DELETE_SUCCESS";
    }

}

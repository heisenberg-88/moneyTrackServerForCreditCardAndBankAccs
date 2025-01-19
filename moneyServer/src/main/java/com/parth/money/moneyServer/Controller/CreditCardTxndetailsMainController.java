package com.parth.money.moneyServer.Controller;

import com.parth.money.moneyServer.Entity.CreditCardTopTxnDetailsMain;
import com.parth.money.moneyServer.Entity.CreditCardTxnDetailsMain;
import com.parth.money.moneyServer.Entity.SummaryModel;
import com.parth.money.moneyServer.Repository.CreditCardTopTxnDetailsMainRepository;
import com.parth.money.moneyServer.Repository.CreditCardTxnDetailsMainRepository;
import com.parth.money.moneyServer.Repository.MoneyServerPropertiesDataRepository;
import com.parth.money.moneyServer.Utils.CCEmiUtility;
import com.parth.money.moneyServer.Utils.SummaryUtility;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.*;

@RestController
@RequestMapping("/moneyServer/CreditCardTxnDetails")
public class CreditCardTxndetailsMainController {

    @Autowired
    CreditCardTxnDetailsMainRepository creditCardTxnDetailsMainRepository;

    @Autowired
    MoneyServerPropertiesDataRepository moneyServerPropertiesDataRepository;

    @Autowired
    CreditCardTopTxnDetailsMainRepository creditCardTopTxnDetailsMainRepository;

    @Autowired
    CCEmiUtility ccEmiUtility;

    @Autowired
    SummaryUtility summaryUtility;

    @Autowired
    EntityManager entityManager;

    List<String> months = Arrays.asList(
            "January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December"
    );


    //GET txns
    @GetMapping("/txn/{id}")
    public CreditCardTxnDetailsMain getTxnbyId(@PathVariable String id){
        return creditCardTxnDetailsMainRepository.findByTxnId(id);
    }

    @GetMapping("/txn/allTxns")
    public List<CreditCardTxnDetailsMain> getAll(){
        List<CreditCardTxnDetailsMain> returnList =  creditCardTxnDetailsMainRepository.findAll();
        Collections.sort(returnList, Comparator.comparing(CreditCardTxnDetailsMain::getTxnBillingYearINTEGER,Comparator.reverseOrder())
                .thenComparing(CreditCardTxnDetailsMain::getTxnBillingMonthINTEGER,Comparator.reverseOrder()));
        return returnList;
    }

    @GetMapping("/txn")
    public List<CreditCardTxnDetailsMain> getTxnbyMonthAndYear(@RequestParam String month, @RequestParam String year){
        List<CreditCardTxnDetailsMain> returnList = creditCardTxnDetailsMainRepository.findByTxnBillingMonthAndTxnBillingYear(month,year);
        Collections.sort(returnList, Comparator.comparing(CreditCardTxnDetailsMain::getTxnBillingYearINTEGER,Comparator.reverseOrder())
                .thenComparing(CreditCardTxnDetailsMain::getTxnBillingMonthINTEGER,Comparator.reverseOrder()));
        return returnList;
    }

    @GetMapping("/txn/Summary")
    public SummaryModel getSummaryByMonthAndYear(@RequestParam(value="month",required=false,defaultValue="X")String month, @RequestParam(value="year",required=false,defaultValue="X")String year){
        SummaryModel summaryModel = summaryUtility.getSummaryFromTxns(month,year);
        return summaryModel;
    }

    @GetMapping("/txn/Summary/allTxns")
    public List<SummaryModel> getSummaryAlltxns(){
        return summaryUtility.getSummarylist();
    }


    @GetMapping("/txn/Toptxns/allTxns")
    public List<CreditCardTopTxnDetailsMain> getAllTopTxns(){
        List<CreditCardTopTxnDetailsMain> returnList = creditCardTopTxnDetailsMainRepository.findAll();
        Collections.sort(returnList,Comparator.comparing(CreditCardTopTxnDetailsMain::getTxnAmount).reversed());
        return returnList;
    }



    // POST txns
    @Transactional
    @PostMapping("/txn")
    public CreditCardTxnDetailsMain addNewTxn(@RequestBody CreditCardTxnDetailsMain entity){
        if(entity.getTxnIsEmi()){
            entity.setTxnIsEmi(false);
        }
        String typeOfCard = null;
        if(entity.getTxnCCused().startsWith("AmazonPay")){
            typeOfCard = "AMZNPAYICICI";
        }
        if(entity.getTxnCCused().startsWith("HDFC")){
            typeOfCard = "REGALIAGOLD";
        }
        if(entity.getTxnCCused().startsWith("Standard")){
            typeOfCard = "STANDCHARTULTIMATE";
        }
        if(entity.getTxnCCused().startsWith("HSBC")){
            typeOfCard = "HSBCVISAPLAT";
        }
        if(entity.getTxnCCused().startsWith("ONECard")){
            typeOfCard = "ONECARDMETAL";
        }
        if(entity.getTxnCCused().startsWith("Swiggy")){
            typeOfCard = "SWIGGYHDFC";
        }
        if(entity.getTxnCCused().startsWith("IRCTC")){
            typeOfCard = "IRCTCSBI";
        }
        if(entity.getTxnCCused().startsWith("YesBank")){
            typeOfCard = "YESELITEPLUS";
        }
        if(entity.getTxnCCused().startsWith("RBL")){
            typeOfCard = "RBLWORLDSAFARI";
        }
        String currDBlastusedYear = moneyServerPropertiesDataRepository.findById(typeOfCard+"lastUsedyear").getValue();
        String currDBlastusedMonth = moneyServerPropertiesDataRepository.findById(typeOfCard+"lastUsedmonth").getValue();

        String stopperEMIMonthinDB = moneyServerPropertiesDataRepository.findById("emilastUsedmonth").getValue();
        String stopperEMIYearinDB = moneyServerPropertiesDataRepository.findById("emilastUsedyear").getValue();

        if(entity.getTxnBillingYearINTEGER()==Integer.parseInt(currDBlastusedYear)){
            if((entity.getTxnBillingMonthINTEGER()-1)<months.indexOf(currDBlastusedMonth)){
               return null;
            }
        } else if (entity.getTxnBillingYearINTEGER()<Integer.parseInt(currDBlastusedYear)) {
            return null;
        }
        Query query = entityManager.createNamedQuery("MoneyServerPropertiesData.updateLastUsedMonthYear");
        if(!currDBlastusedMonth.equalsIgnoreCase(entity.getTxnBillingMonth())){
            query.setParameter("newData",entity.getTxnBillingMonth());
            query.setParameter("id",typeOfCard+"lastUsedmonth");
            query.executeUpdate();
        }
        if(!currDBlastusedYear.equalsIgnoreCase(entity.getTxnBillingYear())){
            query.setParameter("newData",entity.getTxnBillingYear());
            query.setParameter("id",typeOfCard+"lastUsedyear");
            query.executeUpdate();
        }

        if(entity.getTxnBillingYearINTEGER()>Integer.parseInt(stopperEMIYearinDB)){
            query.setParameter("newData",entity.getTxnBillingMonth());
            query.setParameter("id","emilastUsedmonth");
            query.executeUpdate();

            query.setParameter("newData",entity.getTxnBillingYear());
            query.setParameter("id","emilastUsedyear");
            query.executeUpdate();
        }else if (entity.getTxnBillingYearINTEGER()==Integer.parseInt(stopperEMIYearinDB)){
            if((entity.getTxnBillingMonthINTEGER()-1)>months.indexOf(stopperEMIMonthinDB)){
                query.setParameter("newData",entity.getTxnBillingMonth());
                query.setParameter("id","emilastUsedmonth");
                query.executeUpdate();
            }
        }

        CreditCardTxnDetailsMain returnEntity =  creditCardTxnDetailsMainRepository.save(entity);
        CreditCardTopTxnDetailsMain topTxnEntity = new CreditCardTopTxnDetailsMain();
        topTxnEntity.setTxnId(returnEntity.getTxnId());
        topTxnEntity.setTxnDetails(returnEntity.getTxnDetails());
        topTxnEntity.setTxnAmount(returnEntity.getTxnAmount());
        topTxnEntity.setCcorbank("C");
        creditCardTopTxnDetailsMainRepository.save(topTxnEntity);

        return returnEntity;
    }

    @Transactional
    @PostMapping("/txn/addEMItxn")
    public List<CreditCardTxnDetailsMain> addEMItxn(@RequestParam Integer noOfEMIs, @RequestBody CreditCardTxnDetailsMain entity){
        if(!entity.getTxnIsEmi()){
            entity.setTxnIsEmi(true);
        }
        String typeOfCard = null;
        if(entity.getTxnCCused().startsWith("AmazonPay")){
            typeOfCard = "AMZNPAYICICI";
        }
        if(entity.getTxnCCused().startsWith("HDFC")){
            typeOfCard = "REGALIAGOLD";
        }
        if(entity.getTxnCCused().startsWith("Standard")){
            typeOfCard = "STANDCHARTULTIMATE";
        }
        if(entity.getTxnCCused().startsWith("HSBC")){
            typeOfCard = "HSBCVISAPLAT";
        }
        if(entity.getTxnCCused().startsWith("ONECard")){
            typeOfCard = "ONECARDMETAL";
        }
        if(entity.getTxnCCused().startsWith("Swiggy")){
            typeOfCard = "SWIGGYHDFC";
        }
        if(entity.getTxnCCused().startsWith("IRCTC")){
            typeOfCard = "IRCTCSBI";
        }
        if(entity.getTxnCCused().startsWith("YesBank")){
            typeOfCard = "YESELITEPLUS";
        }
        if(entity.getTxnCCused().startsWith("RBL")){
            typeOfCard = "RBLWORLDSAFARI";
        }
        String currDBlastusedYear = moneyServerPropertiesDataRepository.findById(typeOfCard+"lastUsedyear").getValue();
        String currDBlastusedMonth = moneyServerPropertiesDataRepository.findById(typeOfCard+"lastUsedmonth").getValue();

        String stopperEMIMonthinDB = moneyServerPropertiesDataRepository.findById("emilastUsedmonth").getValue();
        String stopperEMIYearinDB = moneyServerPropertiesDataRepository.findById("emilastUsedyear").getValue();

        if(entity.getTxnBillingYearINTEGER()==Integer.parseInt(currDBlastusedYear)){
            if((entity.getTxnBillingMonthINTEGER()-1)<months.indexOf(currDBlastusedMonth)){
                return null;
            }
        } else if (entity.getTxnBillingYearINTEGER()<Integer.parseInt(currDBlastusedYear)) {
            return null;
        }
        String monthNewDB = entity.getTxnBillingMonth();
        String yearNewDB = entity.getTxnBillingYear();
        BigDecimal noOfEmisBIGDECIMAL = new BigDecimal(noOfEMIs);

        List<CreditCardTxnDetailsMain> returnList = ccEmiUtility.addEmitxnsUtil(entity,noOfEMIs);

        Query query = entityManager.createNamedQuery("MoneyServerPropertiesData.updateLastUsedMonthYear");
        if(!currDBlastusedMonth.equalsIgnoreCase(monthNewDB)){
            query.setParameter("newData",monthNewDB);
            query.setParameter("id",typeOfCard+"lastUsedmonth");
            query.executeUpdate();
        }
        if(!currDBlastusedYear.equalsIgnoreCase(yearNewDB)){
            query.setParameter("newData",yearNewDB);
            query.setParameter("id",typeOfCard+"lastUsedyear");
            query.executeUpdate();
        }

        if(Integer.parseInt(returnList.get(returnList.size()-1).getTxnBillingYear())>Integer.parseInt(stopperEMIYearinDB)){
            query.setParameter("newData",returnList.get(returnList.size()-1).getTxnBillingMonth());
            query.setParameter("id","emilastUsedmonth");
            query.executeUpdate();

            query.setParameter("newData",returnList.get(returnList.size()-1).getTxnBillingYear());
            query.setParameter("id","emilastUsedyear");
            query.executeUpdate();
        }else if (Integer.parseInt(returnList.get(returnList.size()-1).getTxnBillingYear())==Integer.parseInt(stopperEMIYearinDB)){
            if((returnList.get(returnList.size()-1).getTxnBillingMonthINTEGER()-1)>months.indexOf(stopperEMIMonthinDB)){
                query.setParameter("newData",returnList.get(returnList.size()-1).getTxnBillingMonth());
                query.setParameter("id","emilastUsedmonth");
                query.executeUpdate();
            }
        }


        CreditCardTopTxnDetailsMain topTxnEntity = new CreditCardTopTxnDetailsMain();
        topTxnEntity.setTxnId(returnList.get(0).getTxnEmiId());
        topTxnEntity.setTxnDetails(returnList.get(0).getTxnDetails());
        topTxnEntity.setTxnAmount(returnList.get(0).getTxnAmount().multiply(noOfEmisBIGDECIMAL));
        topTxnEntity.setCcorbank("C");
        creditCardTopTxnDetailsMainRepository.save(topTxnEntity);

        return returnList;
    }


    //DELETE txns
    @Transactional
    @DeleteMapping("/txn/txnDeleteNormal/{id}")
    public String txnDeleteNormal(@PathVariable String id){
        CreditCardTxnDetailsMain responseFromID = creditCardTxnDetailsMainRepository.findByTxnId(id);
        String typeOfCard = null;
        if(responseFromID.getTxnCCused().startsWith("AmazonPay")){
            typeOfCard = "AMZNPAYICICI";
        }
        if(responseFromID.getTxnCCused().startsWith("HDFC")){
            typeOfCard = "REGALIAGOLD";
        }
        if(responseFromID.getTxnCCused().startsWith("Standard")){
            typeOfCard = "STANDCHARTULTIMATE";
        }
        if(responseFromID.getTxnCCused().startsWith("HSBC")){
            typeOfCard = "HSBCVISAPLAT";
        }
        if(responseFromID.getTxnCCused().startsWith("ONECard")){
            typeOfCard = "ONECARDMETAL";
        }
        if(responseFromID.getTxnCCused().startsWith("Swiggy")){
            typeOfCard = "SWIGGYHDFC";
        }
        if(responseFromID.getTxnCCused().startsWith("IRCTC")){
            typeOfCard = "IRCTCSBI";
        }
        if(responseFromID.getTxnCCused().startsWith("YesBank")){
            typeOfCard = "YESELITEPLUS";
        }
        if(responseFromID.getTxnCCused().startsWith("RBL")){
            typeOfCard = "RBLWORLDSAFARI";
        }
        String currDBlastusedYear = moneyServerPropertiesDataRepository.findById(typeOfCard+"lastUsedyear").getValue();
        String currDBlastusedMonth = moneyServerPropertiesDataRepository.findById(typeOfCard+"lastUsedmonth").getValue();
        if(responseFromID.getTxnBillingYearINTEGER()!=Integer.parseInt(currDBlastusedYear)){
            return "DELETE_ERROR";
        }else{
            if((responseFromID.getTxnBillingMonthINTEGER()-1)<months.indexOf(currDBlastusedMonth)){
                return "DELETE_ERROR";
            }
        }
        creditCardTxnDetailsMainRepository.deleteByCCid(id);
        creditCardTopTxnDetailsMainRepository.deleteTopTxnByCCid(id);
        return "DELETE_SUCCESS";
    }

    @Transactional
    @DeleteMapping("/txn/txnDeleteEmi/{id}")
    public String txnDeleteEmi(@PathVariable String id){
        CreditCardTxnDetailsMain responseFromID = creditCardTxnDetailsMainRepository.findByTxnId(id);
        String typeOfCard = null;
        if(responseFromID.getTxnCCused().startsWith("AmazonPay")){
            typeOfCard = "AMZNPAYICICI";
        }
        if(responseFromID.getTxnCCused().startsWith("HDFC")){
            typeOfCard = "REGALIAGOLD";
        }
        if(responseFromID.getTxnCCused().startsWith("Standard")){
            typeOfCard = "STANDCHARTULTIMATE";
        }
        if(responseFromID.getTxnCCused().startsWith("HSBC")){
            typeOfCard = "HSBCVISAPLAT";
        }
        if(responseFromID.getTxnCCused().startsWith("ONECard")){
            typeOfCard = "ONECARDMETAL";
        }
        if(responseFromID.getTxnCCused().startsWith("Swiggy")){
            typeOfCard = "SWIGGYHDFC";
        }
        if(responseFromID.getTxnCCused().startsWith("IRCTC")){
            typeOfCard = "IRCTCSBI";
        }
        if(responseFromID.getTxnCCused().startsWith("YesBank")){
            typeOfCard = "YESELITEPLUS";
        }
        if(responseFromID.getTxnCCused().startsWith("RBL")){
            typeOfCard = "RBLWORLDSAFARI";
        }
        String currDBlastusedYear = moneyServerPropertiesDataRepository.findById(typeOfCard+"lastUsedyear").getValue();
        String currDBlastusedMonth = moneyServerPropertiesDataRepository.findById(typeOfCard+"lastUsedmonth").getValue();
        if(responseFromID.getTxnBillingYearINTEGER()!=Integer.parseInt(currDBlastusedYear)){
            return "DELETE_ERROR";
        }else{
            if((responseFromID.getTxnBillingMonthINTEGER()-1)<months.indexOf(currDBlastusedMonth)){
                return "DELETE_ERROR";
            }
        }
        String emiId = responseFromID.getTxnEmiId();
        creditCardTxnDetailsMainRepository.deleteByCCEMIid(emiId);
        creditCardTopTxnDetailsMainRepository.deleteTopTxnByCCEMIid(emiId);
        return "DELETE_SUCCESS";
    }

}

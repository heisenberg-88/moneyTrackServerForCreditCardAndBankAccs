package com.parth.money.moneyServer.Utils;


import com.parth.money.moneyServer.Entity.CreditCardTxnDetailsMain;
import com.parth.money.moneyServer.Repository.CreditCardTxnDetailsMainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CCEmiUtility {

    @Autowired
    CreditCardTxnDetailsMainRepository creditCardTxnDetailsMainRepository;

    List<String> months = Arrays.asList(
            "January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December"
    );

    public List<CreditCardTxnDetailsMain> addEmitxnsUtil(CreditCardTxnDetailsMain entity,Integer noOfEmis){
        UUID uuid = UUID.randomUUID();
        Integer currEmiNo = 1;
        Integer totalEmiNo = noOfEmis;
        int currMonthIndex = months.indexOf(entity.getTxnBillingMonth());
        List<CreditCardTxnDetailsMain> cc = new ArrayList<>();
        while(noOfEmis>0){
            CreditCardTxnDetailsMain tempEntity = new CreditCardTxnDetailsMain();
            tempEntity.setTxnIsEmi(true);
            tempEntity.setTxnBillingYear(entity.getTxnBillingYear());
            tempEntity.setTxnBillingMonth(entity.getTxnBillingMonth());
            tempEntity.setTxnAmount(entity.getTxnAmount());
            tempEntity.setTxnDetails(entity.getTxnDetails()+" ("+currEmiNo.toString()+"/"+totalEmiNo.toString()+") ");
            tempEntity.setTxnCCused(entity.getTxnCCused());
            tempEntity.setTxnEmiId(uuid.toString());

            cc.add(tempEntity);
            if(currMonthIndex%12==11){
                int currYear = Integer.parseInt(entity.getTxnBillingYear());
                entity.setTxnBillingYear(String.valueOf(currYear+1));
            }
            currMonthIndex+=1;
            entity.setTxnBillingMonth(months.get(currMonthIndex%12));
            noOfEmis-=1;
            currEmiNo+=1;
        }
        return creditCardTxnDetailsMainRepository.saveAll(cc);
    }
}

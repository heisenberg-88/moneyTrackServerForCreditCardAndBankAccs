package com.parth.money.moneyServer.Repository;

import com.parth.money.moneyServer.Entity.CreditCardTxnDetailsMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardTxnDetailsMainRepository extends JpaRepository<CreditCardTxnDetailsMain, Long> {
    CreditCardTxnDetailsMain findByTxnId(String txnId);

    List<CreditCardTxnDetailsMain> findByTxnBillingMonthAndTxnBillingYear(String month,String year);

    @Modifying
    @Query("delete from CreditCardTxnDetailsMain a where a.txnId = :id")
    void deleteByCCid(String id);

    @Modifying
    @Query("delete from CreditCardTxnDetailsMain a where a.txnEmiId = :emiid")
    void deleteByCCEMIid(String emiid);

}

package com.parth.money.moneyServer.Repository;

import com.parth.money.moneyServer.Entity.CreditCardTopTxnDetailsMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardTopTxnDetailsMainRepository extends JpaRepository<CreditCardTopTxnDetailsMain, Long> {

    @Modifying
    @Query("delete from CreditCardTopTxnDetailsMain a where a.txnId = :id")
    void deleteTopTxnByCCid(String id);

    @Modifying
    @Query("delete from CreditCardTopTxnDetailsMain a where a.txnId = :emiid")
    void deleteTopTxnByCCEMIid(String emiid);
}

package com.parth.money.moneyServer.Repository;

import com.parth.money.moneyServer.Entity.BankTxnDetailsMain;
import com.parth.money.moneyServer.Entity.CreditCardTxnDetailsMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankTxnDetailsMainRepository extends JpaRepository<BankTxnDetailsMain, Long> {

    BankTxnDetailsMain findByBanktxnId(String txnId);
    List<BankTxnDetailsMain> findByBanktxnBillingMonthAndBanktxnBillingYear(String month, String year);

    @Modifying
    @Query("delete from BankTxnDetailsMain a where a.banktxnId = :id")
    void deleteByBankTxnid(String id);
}

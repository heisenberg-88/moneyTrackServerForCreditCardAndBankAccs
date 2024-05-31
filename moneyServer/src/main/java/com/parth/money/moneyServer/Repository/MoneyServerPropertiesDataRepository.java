package com.parth.money.moneyServer.Repository;

import com.parth.money.moneyServer.Entity.MoneyServerPropertiesData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneyServerPropertiesDataRepository extends JpaRepository<MoneyServerPropertiesData, Long> {

    MoneyServerPropertiesData findById(String id);
}

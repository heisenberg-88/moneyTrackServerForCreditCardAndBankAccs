package com.parth.money.moneyServer.Entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="money_server_properties")
@NamedQuery(name = "MoneyServerPropertiesData.updateLastUsedMonthYear",
        query = "UPDATE MoneyServerPropertiesData a SET a.value = :newData WHERE a.id = :id")
@NamedQuery(name = "MoneyServerPropertiesData.updateAvlBalBybankAccName",
            query = "UPDATE MoneyServerPropertiesData a SET a.value = :newData WHERE a.id = :id")
public class MoneyServerPropertiesData implements Serializable {

    @Id
    @Column(name="id")
    private String id;

    @Column(name="key")
    private String key;

    @Column(name="value")
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

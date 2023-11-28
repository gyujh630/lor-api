package com.leagueofrestaurant.web.store.dto;

import com.leagueofrestaurant.web.common.Status;
import com.leagueofrestaurant.web.store.domain.Address;
import lombok.Getter;

@Getter
public class StoreSearchCondition {
    private String name;
    private Address address;

    public StoreSearchCondition(String name, Address address) {
        this.name = name;
        this.address = address;
    }
}
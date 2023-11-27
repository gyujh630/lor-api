package com.leagueofrestaurant.web.store.repository;

import com.leagueofrestaurant.web.store.domain.Store;
import com.leagueofrestaurant.web.store.dto.StoreSearchCondition;

import java.util.List;

public interface StoreRepositoryCustom {
    public List<Store> findStoreListByCondition(StoreSearchCondition condition);
}

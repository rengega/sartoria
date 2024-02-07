package com.swe.sartoria.buisiness_logic;

import com.swe.sartoria.dao.DAO;
import com.swe.sartoria.model_domain.Costumer;



public class CostumerConstroller {
    private DAO dao;

    public CostumerConstroller(DAO dao) {
        this.dao = dao;
    }

    public void addCostumer(Costumer consumer){
        dao.addCostumer(consumer);
    }

    public void deleteCostumer(long id){
        dao.deleteCostumer(id);
    }

    public void updateConsumer(Costumer costumer){
        dao.updateCostumer(costumer);
    }

    public Costumer getCostumer(long id){
        return dao.getCostumer(id);
    }
}

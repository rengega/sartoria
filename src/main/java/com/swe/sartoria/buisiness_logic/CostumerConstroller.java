package com.swe.sartoria.buisiness_logic;

import com.swe.sartoria.dao.DAO;
import com.swe.sartoria.model_domain.Costumer;

import java.util.List;

// TODO: Exepction handling

public class CostumerConstroller {
    private DAO dao;

    public CostumerConstroller(DAO dao) {
        this.dao = dao;
    }

    public void addCostumer(Costumer consumer){

        dao.addCostumer(consumer);
    }

    public void deleteCostumer(Costumer costumer){
        dao.deleteCostumer(costumer.getId());
    }

    public void updateConsumer(Costumer costumer){
        dao.updateCostumer(costumer);
    }

    public Costumer getCostumer(long id){
        return dao.getCostumer(id);
    }

    public List<Costumer> getAllCostumers(){
        return dao.getAllCostumers();
    }

    public List<Costumer> searchCostumer(String search){
        return dao.findCostumerBySearch(search);
    }
}

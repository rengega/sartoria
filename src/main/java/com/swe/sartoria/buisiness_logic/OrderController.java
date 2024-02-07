package com.swe.sartoria.buisiness_logic;

import com.swe.sartoria.dao.DAO;
import com.swe.sartoria.model_domain.Order;

import java.util.List;

public class OrderController {
    private DAO myDao;

    public OrderController(DAO dao){
        myDao = dao;
    }
    public void SaveOrder(Order o){
        myDao.addOrder(o);
    }

    public void DeleteOrder(long id){
        myDao.deleteOrder(id);
    }

    public void UpdateOrder(Order o){
        myDao.updateOrder(o);
    }

    public Order GetOrder(long id){
        return myDao.getOrder(id);
    }

    public List<Order> GetOrdersByCostumer(String name){
        return myDao.getOrdersByCostumerName(name);
    }

}

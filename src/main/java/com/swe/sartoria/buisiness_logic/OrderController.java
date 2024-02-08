package com.swe.sartoria.buisiness_logic;

import com.swe.sartoria.dao.DAO;
import com.swe.sartoria.model_domain.Order;
import jakarta.transaction.Transactional;

import java.util.List;


// TODO: Exepction handling
public class OrderController {
    private DAO myDao;

    public OrderController(DAO dao){
        myDao = dao;
    }
    public void addOrder(Order o){
        myDao.addOrder(o);
    }

    public void DeleteOrder(Order o){
        myDao.deleteOrder(o.getId());
    }

    public void UpdateOrder(Order o){
        myDao.updateOrder(o);
    }

    public Order GetOrder(long id){
        return myDao.getOrder(id);
    }

    public List<Order> searchOrderByCostumer(String search){
        return myDao.getOrdersByCostumerString(search);
    }


    public List<Order> getAllOrders(){
        return myDao.getAllOrders();
    }
}

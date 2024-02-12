package com.swe.sartoria.service;

import com.swe.sartoria.dto.OrderDTO;
import com.swe.sartoria.dto.OrderResponse;

public interface OrderService {
    OrderResponse getAllOrders(int pageNo, int pageSize);
    OrderResponse searchByCostumerString(String costumer, int pageNo, int pageSize);
    OrderResponse getByCostumerId(Long costumerId, int pageNo, int pageSize);
    OrderDTO getOrderById(Long id);
    OrderDTO createOrder(OrderDTO order);
    OrderDTO updateOrder(OrderDTO order);
    void deleteOrder(Long id);
}

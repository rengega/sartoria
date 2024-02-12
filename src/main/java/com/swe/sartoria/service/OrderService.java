package com.swe.sartoria.service;

import com.swe.sartoria.dto.OrderDTO;
import com.swe.sartoria.dto.OrderResponse;
import com.swe.sartoria.model.Order;

public interface OrderService {
    OrderResponse getAllOrders(int pageNo, int pageSize);
    OrderResponse searchByCostumerString(String costumer, int pageNo, int pageSize);
    OrderResponse getByCostumerId(Long costumerId, int pageNo, int pageSize);
    OrderDTO getOrderById(Long id);
    OrderDTO addOrder(OrderDTO order);
    OrderDTO updateOrder(OrderDTO order, Long id);
    void deleteOrder(Long id);
    Order mapToEntity(OrderDTO order);

    OrderDTO mapToDTO(Order order);
}

package com.swe.sartoria.service.impl;


// TODO: Exception handling

import com.swe.sartoria.dto.CostumerDTO;
import com.swe.sartoria.dto.JobDTO;
import com.swe.sartoria.dto.OrderDTO;
import com.swe.sartoria.dto.OrderResponse;
import com.swe.sartoria.model.Job;
import com.swe.sartoria.model.Order;
import com.swe.sartoria.repository.OrderRepository;
import com.swe.sartoria.service.CostumerService;
import com.swe.sartoria.service.JobService;
import com.swe.sartoria.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private CostumerService costumerService;
    private JobService jobService;
    @Autowired
    public OrderServiceImpl (OrderRepository orderRepository, CostumerService costumerService, JobService jobService) {
        this.orderRepository = orderRepository;
        this.costumerService = costumerService;
        this.jobService = jobService;
    }


    @Override
    public OrderDTO addOrder(OrderDTO order) {
        Order newOrder = mapToEntity(order);
        newOrder = orderRepository.save(newOrder);
        return mapToDTO(newOrder);
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            return null;
        }
        return mapToDTO(order);
    }

    @Override
    public OrderDTO updateOrder(OrderDTO orderDTO, Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            return null;
        }
        order = mapToEntity(orderDTO);
        order.setId(id);
        order = orderRepository.save(order);
        return mapToDTO(order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public OrderResponse getAllOrders(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Order> orderPage = orderRepository.findAll(pageable);
        List<Order> ordersList = orderPage.getContent();
        List<OrderDTO> content = ordersList.stream().map(o -> mapToDTO(o)).collect(Collectors.toList());
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setContent(content);
        orderResponse.setPageNo(orderPage.getNumber());
        orderResponse.setPageSize(orderPage.getSize());
        orderResponse.setTotalElements(orderPage.getTotalElements());
        orderResponse.setTotalPages(orderPage.getTotalPages());

        return orderResponse;
    }

    @Override
    public OrderResponse searchByCostumerString(String search, int pageNo, int pageSize) {
        List<Long> costumerIds = costumerService.searchCostumer(search, pageNo, pageSize).getContent().stream().map(c -> c.getId()).collect(Collectors.toList());
        List<Order> orders = new ArrayList<>();
        for (Long id : costumerIds) {
            orders.addAll(orderRepository.findByCostumerId(id));
        }
        List<OrderDTO> content = orders.stream().map(o -> mapToDTO(o)).collect(Collectors.toList());
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setContent(content);
        orderResponse.setPageNo(0);
        orderResponse.setPageSize(content.size());
        orderResponse.setTotalElements(content.size());
        orderResponse.setTotalPages(1);
        return orderResponse;
    }

    @Override
    public OrderResponse getByCostumerId(Long id, int pageNo, int pageSize) {
        List<Order> orders = orderRepository.findByCostumerId(id);
        List<OrderDTO> content = orders.stream().map(o -> mapToDTO(o)).collect(Collectors.toList());
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setContent(content);
        orderResponse.setPageNo(0);
        orderResponse.setPageSize(content.size());
        orderResponse.setTotalElements(content.size());
        orderResponse.setTotalPages(1);

        return orderResponse;
    }


    @Override
    public Order mapToEntity(OrderDTO orderDTO) {
        Order order = new Order();
        CostumerDTO cost_dto =  costumerService.findCostumerById(orderDTO.getCostumer().getId());
        order.setCostumer(costumerService.mapToEntity(cost_dto));
        List<JobDTO> job_dto = orderDTO.getJobs();
        List<Job> jobs = job_dto.stream().map(j -> jobService.mapToEntity(j)).collect(Collectors.toList());
        order.setJobs(jobs);
        order.setDescription(orderDTO.getDescription());
        order.setDiscount(orderDTO.getDiscount());
        order.setStatus(orderDTO.getStatus());
        order.setDueDate(orderDTO.getDueDate());
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setId(orderDTO.getId());
        return order;
    }

    @Override
    public OrderDTO mapToDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        CostumerDTO cost_dto =  costumerService.mapToDTO(order.getCostumer());
        orderDTO.setCostumer(cost_dto);
        List<Job> jobs = order.getJobs();
        List<JobDTO> job_dto = jobs.stream().map(j -> jobService.mapToDto(j)).collect(Collectors.toList());
        orderDTO.setJobs(job_dto);
        orderDTO.setDescription(order.getDescription());
        orderDTO.setDiscount(order.getDiscount());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setDueDate(order.getDueDate());
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setId(order.getId());
        return orderDTO;
    }

}

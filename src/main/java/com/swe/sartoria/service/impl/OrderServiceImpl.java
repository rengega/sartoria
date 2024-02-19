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
import com.swe.sartoria.service.mail_service.MailService;
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
    private MailService mailService;
    @Autowired
    public OrderServiceImpl (OrderRepository orderRepository, CostumerService costumerService, JobService jobService, MailService mailService) {
        this.orderRepository = orderRepository;
        this.costumerService = costumerService;
        this.jobService = jobService;
        this.mailService = mailService;
    }


    @Override
    public OrderDTO addOrder(OrderDTO orderDTO) {
        Order newOrder = mapToEntity(orderDTO);
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
        if (order.getStatus().equals("COMPLETED")) {
            mailService.notifyCostumer(order);
        }
        return mapToDTO(order);
    }

    @Override
    public void deleteOrder(Long id) {
        Order toDelete = orderRepository.findById(id).orElse(null);
        // TODO: Exception handling
        if (toDelete != null) {
            orderRepository.delete(toDelete);
        }
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
        //get costumer ids that match search
        List<Long> costumerIds = costumerService.searchCostumer(search, pageNo, pageSize).getContent().stream().map(c -> c.getId()).collect(Collectors.toList());
        // TODO: swap list with set

        //get orders that match costumer ids
        List<Order> orders = new ArrayList<>();
        for (Long id : costumerIds) {
            orders.addAll(orderRepository.findByCostumerId(id));
        }
        //craft response
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
        Order order = Order.builder().build();
        CostumerDTO cost_dto =  costumerService.findCostumerById(orderDTO.getCostumer().getId());
        order.setCostumer(costumerService.mapToEntity(cost_dto));
        List<JobDTO> job_dto = orderDTO.getJobs();
        List<Job> jobs = new ArrayList<>();

        for (JobDTO jobDTO : job_dto) {
            Job job = jobService.mapToEntity(jobDTO);
            jobs.add(job);
        }

        order.setJobs(jobs);
        order.setDescription(orderDTO.getDescription());
        order.setDiscount(orderDTO.getDiscount());
        order.setStatus(orderDTO.getStatus());
        order.setDueDate(orderDTO.getDueDate());
        if (orderDTO.getId() != null){
            order.setId(orderDTO.getId());
        }
        return order;
    }

    @Override
    public OrderDTO mapToDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        CostumerDTO cost_dto =  costumerService.mapToDTO(order.getCostumer());
        orderDTO.setCostumer(cost_dto);
        List<Job> jobs = order.getJobs();
        List<JobDTO> job_dto = jobs.stream().map(j -> jobService.mapToDTO(j)).collect(Collectors.toList());
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

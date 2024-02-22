package com.swe.sartoria.controller;

import com.swe.sartoria.dto.CostumerDTO;
import com.swe.sartoria.dto.JobDTO;
import com.swe.sartoria.dto.OrderDTO;
import com.swe.sartoria.dto.OrderResponse;
import com.swe.sartoria.model.Costumer;
import com.swe.sartoria.model.Job;
import com.swe.sartoria.model.Order;
import com.swe.sartoria.service.DAO_pure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order")
public class OrdersController {
    private DAO_pure dao;

    @Autowired
    public OrdersController(DAO_pure dao) {
        this.dao = dao;
    }


    // Parsing methods
    private Order mapOrderToEntity(OrderDTO orderDTO) {
        Order order = new Order();
        Costumer costumer = dao.getCostumerById(orderDTO.getCostumer().getId());
        order.setCostumer(costumer);
        List<Job> jobs = new ArrayList<>();
        for (JobDTO jobDTO : orderDTO.getJobs()) {
            Job job = dao.getJobById(jobDTO.getId());
            jobs.add(job);
        }
        order.setId(orderDTO.getId());
        order.setJobs(jobs);
        order.setDescription(orderDTO.getDescription());
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setDueDate(orderDTO.getDueDate());
        order.setDiscount(orderDTO.getDiscount());
        order.setStatus(orderDTO.getStatus());
        order.setPaid(orderDTO.getPaid());
        return order;
    }

    private OrderDTO mapOrderToDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        CostumerDTO costumerDTO = new CostumerDTO();
        costumerDTO.setId(order.getCostumer().getId());
        costumerDTO.setName(order.getCostumer().getName());
        costumerDTO.setSurname(order.getCostumer().getSurname());
        costumerDTO.setEmail(order.getCostumer().getEmail());
        costumerDTO.setPhone(order.getCostumer().getPhone());
        orderDTO.setPaid(order.getPaid());
        orderDTO.setCostumer(costumerDTO);
        List<JobDTO> jobDTOs = new ArrayList<>();
        for (Job job : order.getJobs()) {
            JobDTO jobDTO = new JobDTO();
            jobDTO.setId(job.getId());
            jobDTO.setName(job.getName());
            jobDTO.setDescription(job.getDescription());
            jobDTO.setCategory(job.getCategory());
            jobDTO.setPrice(job.getPrice());
            jobDTOs.add(jobDTO);
        }
        orderDTO.setId(order.getId());
        orderDTO.setJobs(jobDTOs);
        orderDTO.setDescription(order.getDescription());
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setDueDate(order.getDueDate());
        orderDTO.setDiscount(order.getDiscount());
        orderDTO.setStatus(order.getStatus());
        return orderDTO;
    }

    // Endpoints

    @GetMapping("/getOrderStatusById/{id}")
    public ResponseEntity<String> getOrderStatusById(@PathVariable Long id){
        Order order = dao.getOrderById(id);
        return new ResponseEntity<>(order.getStatus(), HttpStatus.OK);
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<OrderResponse> getAllOrders(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    )
    {
        List<Order> listOfAllOrders = dao.getAllOrders();
        if (listOfAllOrders.size() == 0) {
            return ResponseEntity.ok(new OrderResponse());
        }
        List<OrderDTO> orderDTOS = listOfAllOrders.stream().map(this::mapOrderToDTO).collect(Collectors.toList());
        OrderResponse response = new OrderResponse();
        response.setContent(orderDTOS);
        response.setPageNo(pageNo);
        response.setPageSize(pageSize);
        response.setTotalElements(listOfAllOrders.size());
        response.setTotalPages(listOfAllOrders.size() / pageSize);
        response.setLast(listOfAllOrders.size() <= pageSize);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/searchByCostumer/{search}")
    public ResponseEntity<OrderResponse> searchOrdersByCosumerString(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @PathVariable String search
    )
    {
        List<Order> listOfAllOrders = dao.searchByCostumerString(search);
        if (listOfAllOrders.size() == 0) {
            return ResponseEntity.ok(new OrderResponse());
        }
        List<OrderDTO> orderDTOS = listOfAllOrders.stream().map(this::mapOrderToDTO).collect(Collectors.toList());
        OrderResponse response = new OrderResponse();
        response.setContent(orderDTOS);
        response.setPageNo(pageNo);
        response.setPageSize(pageSize);
        response.setTotalElements(listOfAllOrders.size());
        response.setTotalPages(listOfAllOrders.size() / pageSize);
        response.setLast(listOfAllOrders.size() <= pageSize);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getOrdersByCostumerId/{id}")
    public ResponseEntity<OrderResponse> getOrdersByCostumerId(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @PathVariable Long id
    )
    {
        List<Order> listOfAllOrders = dao.getByCostumerId(id);
        if (listOfAllOrders.size() == 0) {
            return ResponseEntity.ok(new OrderResponse());
        }
        List<OrderDTO> orderDTOS = listOfAllOrders.stream().map(this::mapOrderToDTO).collect(Collectors.toList());
        OrderResponse response = new OrderResponse();
        response.setContent(orderDTOS);
        response.setPageNo(pageNo);
        response.setPageSize(pageSize);
        response.setTotalElements(listOfAllOrders.size());
        response.setTotalPages(listOfAllOrders.size() / pageSize);
        response.setLast(listOfAllOrders.size() <= pageSize);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getOrderById/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id){
        return new ResponseEntity<>(mapOrderToDTO(dao.getOrderById(id)), HttpStatus.OK);
    }

    @PostMapping("/addOrder")
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public ResponseEntity<OrderDTO> addOrder(@RequestBody OrderDTO orderDto){
        Order order = mapOrderToEntity(orderDto);
        return new ResponseEntity<>(mapOrderToDTO(dao.addOrder(order)), HttpStatus.CREATED);
    }

    @PutMapping("/updateOrder")
    public ResponseEntity<OrderDTO> updateORder(@RequestBody OrderDTO orderDTO){
        Order order = mapOrderToEntity(orderDTO);
        order = dao.updateOrder(order, order.getId());
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(mapOrderToDTO(order), HttpStatus.OK);
    }

    @DeleteMapping("/deleteOrder/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id){
        dao.deleteOrder(id);
        return new ResponseEntity<>("Order deleted successfully", HttpStatus.OK);
    }

}

package com.swe.sartoria.dao;

import com.swe.sartoria.mail_service.MailService;
import com.swe.sartoria.model_domain.Costumer;
import com.swe.sartoria.model_domain.Job;
import com.swe.sartoria.model_domain.Order;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


// TODO: implement DAO as singleton

@Service
public class DAO {
    // refs to all repos
    private JobRepository jobRepository;
    private CostumerRepository costumerRepository;
    private OrderRepository  orderRepository;
    private MailService mailService;

    @Autowired
    public DAO (JobRepository jobRepository, CostumerRepository costumerRepository, OrderRepository orderRepository, MailService mailService) {
        this.jobRepository = jobRepository;
        this.costumerRepository = costumerRepository;
        this.orderRepository = orderRepository;
        this.mailService = mailService;
    }



    // Methods

    // JOBS

    public void addJob(Job job) {
        jobRepository.save(job);
    }


    public void deleteJob(long id) {
        jobRepository.deleteById(id);
    }

    public void updateJob(Job job) {
        jobRepository.save(job);
    }

    public Job getJob(long id) {
        return jobRepository.findById(id).get();
    }

    public List<Job> getJobByName(String name) {
        return jobRepository.findJobByName(name);
    }

    public List<Job> getJobByCategory(String category) {
        return jobRepository.findJobByCategory(category);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }



    //  COSTUMERS

    public void addCostumer(Costumer costumer) {
        costumerRepository.save(costumer);
    }

    public void deleteCostumer(long id) {
        costumerRepository.deleteById(id);
    }

    public void updateCostumer(Costumer costumer) {
        costumerRepository.save(costumer);
    }

    public Costumer getCostumer(long id) {
        return costumerRepository.findById(id).get();
    }

    public List<Costumer> getAllCostumers() {
        return costumerRepository.findAll();
    }

    public List<Costumer> findCostumerByName(String name) {
        // add exception handling
        return costumerRepository.findCostumerByName(name);
    }

    public List<Costumer> findCostumerBySurname(String surname) {
        // add exception handling
        return costumerRepository.findCostumerBySurname(surname);
    }

    public List<Costumer> findCostumerBySearch(String search) {
        return costumerRepository.findCostumerByString(search);
    }
    public List<Costumer> findCostumerByEmail(String email) {
        // add exception handling
        return costumerRepository.findCostumerByEmail(Long.valueOf(email));
    }



    // ORDERS
    public void addOrder(Order order) {
        orderRepository.save(order);
    }


    public void deleteOrder(long id) {
        orderRepository.deleteById(id);
    }



    public void updateOrder(Order order) {
        orderRepository.save(order);
        if (order.getStatus().equals("completed")) {
            String email = order.getCostumer().getEmail();
            mailService.sendMail(email, "Order Completed", "Your order has been completed");
        }
    }

    public Order getOrder(long id) {
        return orderRepository.findById(id).get();
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // write method to retrieve orders by costumer names


    public List<Order> getOrdersByCostumerName(String name) {
        List<Long> clientsForName = costumerRepository.findIdsByName(name);
        List<Order> orders = new ArrayList<>();

        for (Long id : clientsForName) {
            orders.addAll(orderRepository.findOrderByCostumer(id));
        }
        return orders;
    }

    public List<Order> getOrdersByCostumerSurname(String surname) {
        List<Long> clientsForSurname = costumerRepository.findIdsBySurname(surname);
        List<Order> orders = new ArrayList<>();

        for (Long id : clientsForSurname) {
            orders.addAll(orderRepository.findOrderByCostumer(id));
        }
        return orders;
    }

    public List<Order> getOrdersByCostumerString(String searchString) {
        List<Long> clientsForString = costumerRepository.findIdsByString(searchString);
        List<Order> orders = new ArrayList<>();

        for (Long id : clientsForString) {
            orders.addAll(orderRepository.findOrderByCostumer(id));
        }
        return orders;
    }

    public List<Order> findOrdersByDueDate(Date date) {
        return orderRepository.findOrderByDueDate(date);
    }

}

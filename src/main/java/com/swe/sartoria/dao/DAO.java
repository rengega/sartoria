package com.swe.sartoria.dao;

import com.swe.sartoria.model_domain.Costumer;
import com.swe.sartoria.model_domain.Job;
import com.swe.sartoria.model_domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


// TODO: implement DAO as singleton

@Service
public class DAO {
    // refs to all repos
    private JobRepository jobRepository;
    private CostumerRepository costumerRepository;
    private OrderRepository  orderRepository;

    @Autowired
    public DAO (JobRepository jobRepository, CostumerRepository costumerRepository, OrderRepository orderRepository) {
        this.jobRepository = jobRepository;
        this.costumerRepository = costumerRepository;
        this.orderRepository = orderRepository;
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
        Long id =  jobRepository.findJobByName(name).get(0);
        List<Job> jobs = new ArrayList<>();
        for (Long i : jobRepository.findJobByName(name)) {
            jobs.add(jobRepository.findById(i).get());
        }
        return jobs;
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
        List<Long> ids = costumerRepository.findCostumerByName(name);
        List<Costumer> costumers = new ArrayList<>();
        for (Long id : ids) {
            costumers.add(costumerRepository.findById(id).get());
        }
        return costumers;
    }

    public List<Long> findCostumerBySurname(String surname) {
        return costumerRepository.findCostumerBySurname(surname);
    }

    public List<Long> findCostumerById(Long id) {
        return costumerRepository.findCostumerById(id);
    }

    public void addOrder(Order order) {
        orderRepository.save(order);
    }


    public void deleteOrder(long id) {
        orderRepository.deleteById(id);
    }


    // ORDERS
    public void updateOrder(Order order) {
        orderRepository.save(order);
    }

    public Order getOrder(long id) {
        return orderRepository.findById(id).get();
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // write method to retrieve orders by costumer names

    public List<Order> getOrdersByCostumerName(String name) {
        List<Long> clientsForName = costumerRepository.findCostumerByName(name);
        List<Long> ordersForClient = new ArrayList<>();

        for (Long id : clientsForName) {
            ordersForClient.addAll(orderRepository.findOrderByCostumer(id));
        }

        List<Order> orders = new ArrayList<>();
        for (Long id : ordersForClient) {
            orders.add(orderRepository.findById(id).get());
        }
        return orders;
    }

}

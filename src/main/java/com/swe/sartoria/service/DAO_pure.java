package com.swe.sartoria.service;

import com.swe.sartoria.model.Costumer;
import com.swe.sartoria.model.Job;
import com.swe.sartoria.model.Order;
import com.swe.sartoria.repository.CostumerRepository;
import com.swe.sartoria.repository.JobRepository;
import com.swe.sartoria.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DAO_pure {
    private CostumerRepository costumerRepository;
    private JobRepository jobRepository;
    private OrderRepository orderRepository;
    private MailService mailService;
    public DAO_pure(CostumerRepository costumerRepository, JobRepository jobRepository, OrderRepository orderRepository, MailService mailService){
        this.costumerRepository = costumerRepository;
        this.jobRepository = jobRepository;
        this.orderRepository = orderRepository;
        this.mailService = mailService;
    }

    // COSTUMERS----------------------------------------------------------------
    public Costumer addCostumer(Costumer costumer) {
        return costumerRepository.save(costumer);
    }

    public Costumer findCostumerById(Long id) {
        return costumerRepository.findById(id).orElse(null);

    }

    public Costumer updateCostumer(Costumer costumer, Long id) {
        Costumer costToUpdate = costumerRepository.findById(id).orElse(null);
        if (costumer == null) {
            return null;
        }

        costToUpdate = costumer;
        costToUpdate = costumerRepository.save(costToUpdate);
        return costToUpdate;
    }

    public void deleteCostumer(Long id) {
        Costumer costumer = costumerRepository.findById(id).orElse(null);
        // TODO: Exception handling instead of this
        if (costumer != null) {
            costumerRepository.delete(costumer);
        } else {
            System.out.println("Costumer not found");
        }
    }

    public List<Costumer> getAllCostumers() {
        return costumerRepository.findAll();
    }

    public List<Costumer> searchCostumer(String search){
        return costumerRepository.searchCostumer(search);
    }

    public Costumer getCostumerById(long id) {
        return costumerRepository.findById(id).orElse(null);

    }

    // JOBS----------------------------------------------------------------

    public Job addJob(Job job) {
        return jobRepository.save(job);
    }


    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }

    public Job updateJob(Job job, Long id) {
        Job toUpdate = jobRepository.findById(id).orElse(null);
        if (toUpdate == null) {
            return null;
        }
        toUpdate = job;
        toUpdate = jobRepository.save(toUpdate);
        return toUpdate;
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public void deleteJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        if (job != null) {
            jobRepository.delete(job);
        }
    }

    public List<Job> getJobsByCategory(String category) {
        return jobRepository.findByCategory(category);
    }

    public List<Job> searchJobs(String key){
        return jobRepository.searchJob(key);
    }

    // ORDERS----------------------------------------------------------------

    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public List<Order> getOrdesByCostId(Long id){return orderRepository.findByCostumerId(id);}
    public Order updateOrder(Order order, Long id) {

        Order toUpdate = orderRepository.findById(id).orElse(null);
        if (toUpdate == null) {
            return null;
        }
        toUpdate = order;
        toUpdate = orderRepository.save(toUpdate);
        if (toUpdate.getStatus().equals("COMPLETED")){
            mailService.notifyCostumer(toUpdate);
        }
        return toUpdate;
    }

    public void deleteOrder(Long id) {
        Order toDelete = orderRepository.findById(id).orElse(null);
        // TODO: Exception handling
        if (toDelete != null) {
            orderRepository.delete(toDelete);
        }
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> searchByCostumerString(String search) {
        //get costumer ids that match search
        Set<Long> costumerIds = costumerRepository.searchCostumer(search).stream().map(c -> c.getId()).collect(Collectors.toSet());
        List<Order> orders = new ArrayList<>();
        for (Long id : costumerIds) {
            orders.addAll(orderRepository.findByCostumerId(id));
        }
        return orders;
    }

    public List<Order> getByCostumerId(Long id) {
        return orderRepository.findByCostumerId(id);
    }



}

package com.swe.sartoria.service;

import com.swe.sartoria.dto.CostumerDTO;
import com.swe.sartoria.dto.CostumerResponse;
import com.swe.sartoria.model.Costumer;
import com.swe.sartoria.repository.CostumerRepository;
import com.swe.sartoria.repository.JobRepository;
import com.swe.sartoria.repository.OrderRepository;
import com.swe.sartoria.service.mail_service.MailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public Costumer findCostumerById(long id) {
        return costumerRepository.findById(id).orElse(null);

    }

    public Costumer updateCostumer(Costumer costumer, long id) {
        Costumer costToUpdate = costumerRepository.findById(id).orElse(null);
        if (costumer == null) {
            return null;
        }

        costToUpdate = costumer;
        costToUpdate = costumerRepository.save(costToUpdate);
        return costToUpdate;
    }

    public void deleteCostumer(long id) {
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

}

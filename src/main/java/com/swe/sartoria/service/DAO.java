package com.swe.sartoria.service;

import com.swe.sartoria.dto.*;
import com.swe.sartoria.model.Costumer;
import com.swe.sartoria.model.Job;
import com.swe.sartoria.model.Order;
import com.swe.sartoria.repository.CostumerRepository;
import com.swe.sartoria.repository.JobRepository;
import com.swe.sartoria.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DAO {
    private CostumerRepository costumerRepository;
    private JobRepository jobRepository;
    private OrderRepository orderRepository;
    private MailService mailService;
    public DAO(CostumerRepository costumerRepository, JobRepository jobRepository, OrderRepository orderRepository, MailService mailService){
        this.costumerRepository = costumerRepository;
        this.jobRepository = jobRepository;
        this.orderRepository = orderRepository;
        this.mailService = mailService;
    }


    // COSTUMERS----------------------------------------------------------------
    public CostumerDTO addCostumer(CostumerDTO costumerDTO) {
        Costumer newCostumer = mapCostumerToEntity(costumerDTO);

        newCostumer = costumerRepository.save(newCostumer);

        CostumerDTO responseDTO = mapCostumerToDTO(newCostumer);

        return responseDTO;
    }

    public CostumerDTO findCostumerById(long id) {
        Costumer costumer = costumerRepository.findById(id).orElse(null);
        if (costumer == null) {
            return null;
        }

        CostumerDTO costumerDTO = new CostumerDTO();
        costumerDTO.setId(costumer.getId());
        costumerDTO.setName(costumer.getName());
        costumerDTO.setSurname(costumer.getSurname());
        costumerDTO.setEmail(costumer.getEmail());
        costumerDTO.setPhone(costumer.getPhone());

        return costumerDTO;
    }

    public CostumerDTO updateCostumer(CostumerDTO costumerDTO, long id) {
        Costumer costumer = costumerRepository.findById(costumerDTO.getId()).orElse(null);
        if (costumer == null) {
            return null;
        }

        costumer.setName(costumerDTO.getName());
        costumer.setSurname(costumerDTO.getSurname());
        costumer.setEmail(costumerDTO.getEmail());
        costumer.setPhone(costumerDTO.getPhone());

        costumer = costumerRepository.save(costumer);

        CostumerDTO responseDTO = mapCostumerToDTO(costumer);

        return responseDTO;
    }

    public void deleteCostumer(long id) {
        Costumer costumer = costumerRepository.findById(id).orElse(null);
        // TODO: Exception handling instead of this
        if (costumer != null) {
            costumerRepository.delete(costumer);
        }
    }

    public CostumerResponse getAllCostumers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Costumer> costumerPage = costumerRepository.findAll(pageable);
        List<Costumer> costumerList = costumerPage.getContent();
        List<CostumerDTO> costumerDTOList = costumerList.stream().map(c -> mapCostumerToDTO(c)).collect(Collectors.toList());

        CostumerResponse costumerResponse = new CostumerResponse();
        costumerResponse.setContent(costumerDTOList);
        costumerResponse.setPageNo(costumerPage.getNumber());
        costumerResponse.setPageSize(costumerPage.getSize());
        costumerResponse.setTotalElements(costumerPage.getTotalElements());
        costumerResponse.setTotalPages(costumerPage.getTotalPages());
        costumerResponse.setLast(costumerPage.isLast());

        return costumerResponse;
    }

    public CostumerResponse searchCostumer(String search, int pageNo, int pageSize){
        List<Costumer> costumerList = costumerRepository.searchCostumer(search);
        List<CostumerDTO> costumerDTOList = costumerList.stream().map(c -> mapCostumerToDTO(c)).collect(Collectors.toList());

        CostumerResponse costumerResponse = new CostumerResponse();
        costumerResponse.setContent(costumerDTOList);
        costumerResponse.setPageNo(0);
        costumerResponse.setPageSize(costumerDTOList.size());
        costumerResponse.setTotalElements(costumerDTOList.size());
        costumerResponse.setTotalPages(1);
        costumerResponse.setLast(true);

        return costumerResponse;
    }

    public CostumerDTO getCostumerById(long id) {
        Costumer costumer = costumerRepository.findById(id).orElse(null);
        return mapCostumerToDTO(costumer);
    }

    public CostumerDTO mapCostumerToDTO(Costumer costumer) {
        CostumerDTO costumerDTO = new CostumerDTO();
        costumerDTO.setId(costumer.getId());
        costumerDTO.setName(costumer.getName());
        costumerDTO.setSurname(costumer.getSurname());
        costumerDTO.setEmail(costumer.getEmail());
        costumerDTO.setPhone(costumer.getPhone());
        return costumerDTO;
    }

    public Costumer mapCostumerToEntity(CostumerDTO costumerDTO) {
        Costumer costumer = new Costumer();
        costumer.setId(costumerDTO.getId());
        costumer.setName(costumerDTO.getName());
        costumer.setSurname(costumerDTO.getSurname());
        costumer.setEmail(costumerDTO.getEmail());
        costumer.setPhone(costumerDTO.getPhone());
        return costumer;
    }

    // JOBS----------------------------------------------------------------

    public JobDTO addJob(JobDTO jobDTO) {
        Job newJob = mapJobToEntity(jobDTO);
        Job savedJob = jobRepository.save(newJob);
        return mapJobToDTO(savedJob);


    }


    public JobDTO getJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        return mapJobToDTO(job);
    }

    public JobDTO updateJob(JobDTO jobDTO, Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        if (job == null) {
            return null;
        }

        job.setName(jobDTO.getName());
        job.setDescription(jobDTO.getDescription());
        job.setCategory(jobDTO.getCategory());
        job.setPrice(jobDTO.getPrice());

        Job updatedJob = jobRepository.save(job);
        return mapJobToDTO(updatedJob);
    }

    public JobResponse getAllJobs(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Job> jobs = jobRepository.findAll(pageable);
        List<Job> listOfJobs = jobs.getContent();
        List<JobDTO> content = listOfJobs.stream().map(j -> mapJobToDTO(j)).collect(Collectors.toList());

        JobResponse jobResponse = new JobResponse();
        jobResponse.setContent(content);
        jobResponse.setPageNo(jobs.getNumber());
        jobResponse.setPageSize(jobs.getSize());
        jobResponse.setTotalElements(jobs.getTotalElements());
        jobResponse.setTotalPages(jobs.getTotalPages());
        jobResponse.setLast(jobs.isLast());

        return jobResponse;
    }

    public void deleteJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        if (job != null) {
            jobRepository.delete(job);
        }
    }

    public JobResponse getJobsByCategory(String category) {
        List<Job> jobs = jobRepository.findByCategory(category);
        List<JobDTO> content = jobs.stream().map(j -> mapJobToDTO(j)).collect(Collectors.toList());

        JobResponse jobResponse = new JobResponse();
        jobResponse.setContent(content);
        jobResponse.setPageNo(0);
        jobResponse.setPageSize(content.size());
        jobResponse.setTotalElements(content.size());
        jobResponse.setTotalPages(1);
        jobResponse.setLast(true);

        return jobResponse;
    }

    public JobResponse searchJobs(String key, int pageNo, int pageSize){
        List<Job> jobs = jobRepository.searchJob(key);
        List<JobDTO> content = jobs.stream().map(j -> mapJobToDTO(j)).collect(Collectors.toList());

        JobResponse jobResponse = new JobResponse();
        jobResponse.setContent(content);
        jobResponse.setPageNo(0);
        jobResponse.setPageSize(content.size());
        jobResponse.setTotalElements(content.size());
        jobResponse.setTotalPages(1);
        jobResponse.setLast(true);

        return jobResponse;
    }

    public Job mapJobToEntity(JobDTO dto){
        Job newJob = new Job();
        newJob.setId(dto.getId());
        newJob.setName(dto.getName());
        newJob.setDescription(dto.getDescription());
        newJob.setCategory(dto.getCategory());
        newJob.setPrice(dto.getPrice());
        return newJob;
    }


    public JobDTO mapJobToDTO(Job job){
        JobDTO jobDTO = new JobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setName(job.getName());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setCategory(job.getCategory());
        jobDTO.setPrice(job.getPrice());
        return jobDTO;
    }

    // ORDERS----------------------------------------------------------------

    public OrderDTO addOrder(OrderDTO orderDTO) {
        Order newOrder = mapOrderToEntity(orderDTO);
        newOrder = orderRepository.save(newOrder);
        return mapOrderToDTO(newOrder);
    }

    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            return null;
        }
        return mapOrderToDTO(order);
    }

    public OrderDTO updateOrder(OrderDTO orderDTO, Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            return null;
        }
        order = mapOrderToEntity(orderDTO);
        order.setId(id);
        order = orderRepository.save(order);
        if (order.getStatus().equals("COMPLETED")) {
            mailService.notifyCostumer(order);
        }
        return mapOrderToDTO(order);
    }

    public void deleteOrder(Long id) {
        Order toDelete = orderRepository.findById(id).orElse(null);
        // TODO: Exception handling
        if (toDelete != null) {
            orderRepository.delete(toDelete);
        }
    }

    public OrderResponse getAllOrders(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Order> orderPage = orderRepository.findAll(pageable);
        List<Order> ordersList = orderPage.getContent();
        List<OrderDTO> content = ordersList.stream().map(o -> mapOrderToDTO(o)).collect(Collectors.toList());
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setContent(content);
        orderResponse.setPageNo(orderPage.getNumber());
        orderResponse.setPageSize(orderPage.getSize());
        orderResponse.setTotalElements(orderPage.getTotalElements());
        orderResponse.setTotalPages(orderPage.getTotalPages());

        return orderResponse;
    }

    public OrderResponse searchByCostumerString(String search, int pageNo, int pageSize) {
        //get costumer ids that match search
        List<Long> costumerIds = searchCostumer(search, pageNo, pageSize).getContent().stream().map(c -> c.getId()).collect(Collectors.toList());
        // TODO: swap list with set

        //get orders that match costumer ids
        List<Order> orders = new ArrayList<>();
        for (Long id : costumerIds) {
            orders.addAll(orderRepository.findByCostumerId(id));
        }
        //craft response
        List<OrderDTO> content = orders.stream().map(o -> mapOrderToDTO(o)).collect(Collectors.toList());
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setContent(content);
        orderResponse.setPageNo(0);
        orderResponse.setPageSize(content.size());
        orderResponse.setTotalElements(content.size());
        orderResponse.setTotalPages(1);
        return orderResponse;
    }

    public OrderResponse getByCostumerId(Long id, int pageNo, int pageSize) {
        List<Order> orders = orderRepository.findByCostumerId(id);
        List<OrderDTO> content = orders.stream().map(o -> mapOrderToDTO(o)).collect(Collectors.toList());
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setContent(content);
        orderResponse.setPageNo(0);
        orderResponse.setPageSize(content.size());
        orderResponse.setTotalElements(content.size());
        orderResponse.setTotalPages(1);

        return orderResponse;
    }


    public Order mapOrderToEntity(OrderDTO orderDTO) {
        Order order = Order.builder().build();
        System.out.println(orderDTO.toString());
        CostumerDTO cost_dto = findCostumerById(orderDTO.getCostumer().getId());
        System.out.println(cost_dto.toString());
        order.setCostumer(mapCostumerToEntity(cost_dto));
        List<JobDTO> job_dto = orderDTO.getJobs();
        List<Job> jobs = new ArrayList<>();

        for (JobDTO jobDTO : job_dto) {
            Job job = mapJobToEntity(jobDTO);
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

    public OrderDTO mapOrderToDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        CostumerDTO cost_dto =  mapCostumerToDTO(order.getCostumer());
        orderDTO.setCostumer(cost_dto);
        List<Job> jobs = order.getJobs();
        List<JobDTO> job_dto = jobs.stream().map(j -> mapJobToDTO(j)).collect(Collectors.toList());
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

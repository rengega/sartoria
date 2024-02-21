package com.swe.sartoria.service;

import com.swe.sartoria.dto.*;
import com.swe.sartoria.model.Costumer;
import com.swe.sartoria.model.Job;
import com.swe.sartoria.model.Order;
import com.swe.sartoria.repository.CostumerRepository;
import com.swe.sartoria.repository.JobRepository;
import com.swe.sartoria.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private JobRepository jobRepository;
    @Mock
    private CostumerRepository costumerRepository;
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private DAO dao;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void OrderService_addOrder_OrderDTO() {
        Job job = Job.builder()
                .id(1L)
                .name("job1")
                .description("description1")
                .category("category1")
                .price(100)
                .build();
        JobDTO jobDTO = JobDTO.builder()
                .id(1L)
                .name("job1")
                .description("description1")
                .category("category1")
                .price(100)
                .build();

        Costumer costumer  = Costumer.builder()
                .id(1L)
                .name("Nome1")
                .surname("Cognome1")
                .email("renigega@outlook.it")
                .phone(3280119573L)
                .build();

        CostumerDTO costumerDTO = CostumerDTO.builder()
                .id(1L)
                .name("Nome1")
                .surname("Cognome1")
                .email("renigega@outlook.it")
                .phone(3280119573L)
                .build();

        Order order = Order.builder()
                .description("TestingOrder")
                .status("In attesa")
                .dueDate(new Date())
                .discount(4L)
                .build();
        order.addJob(job);
        order.setCostumer(costumer);

        List<JobDTO> jobDTOList = List.of(jobDTO);

        OrderDTO orderDTO = OrderDTO.builder()
                .id(1L)
                .description("TestingOrder")
                .status("In attesa")
                .jobs(jobDTOList)
                .costumer(costumerDTO)
                .dueDate(new Date())
                .discount(4L)
                .build();



        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(costumerRepository.findById(1L)).thenReturn(java.util.Optional.of(costumer));
        OrderDTO savedOrder = dao.addOrder(orderDTO);

        Assertions.assertNotNull(savedOrder);
        Assertions.assertEquals(savedOrder.getDescription(), orderDTO.getDescription());
        Assertions.assertEquals(savedOrder.getStatus(), orderDTO.getStatus());


    }

    @Test
    public void OrderService_getOrderById_OrderDTO() {
        Job job = Job.builder()
                .id(1L)
                .name("job1")
                .description("description1")
                .category("category1")
                .price(100)
                .build();
        JobDTO jobDTO = JobDTO.builder()
                .id(1L)
                .name("job1")
                .description("description1")
                .category("category1")
                .price(100)
                .build();

        Costumer costumer  = Costumer.builder()
                .id(1L)
                .name("Nome1")
                .surname("Cognome1")
                .email("renigega@outlook.it")
                .phone(3280119573L)
                .build();

        CostumerDTO costumerDTO = CostumerDTO.builder()
                .id(1L)
                .name("Nome1")
                .surname("Cognome1")
                .email("renigega@outlook.it")
                .phone(3280119573L)
                .build();

        Order order = Order.builder()
                .description("TestingOrder")
                .status("In attesa")
                .dueDate(new Date())
                .discount(4L)
                .build();
        order.addJob(job);
        order.setCostumer(costumer);

        List<JobDTO> jobDTOList = List.of(jobDTO);

        OrderDTO orderDTO = OrderDTO.builder()
                .id(1L)
                .description("TestingOrder")
                .status("In attesa")
                .jobs(jobDTOList)
                .costumer(costumerDTO)
                .dueDate(new Date())
                .discount(4L)
                .build();

        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));

        OrderDTO foundOrder = dao.getOrderById(1L);

        Assertions.assertNotNull(foundOrder);
        Assertions.assertEquals(foundOrder.getDescription(), orderDTO.getDescription());
        Assertions.assertEquals(foundOrder.getStatus(), orderDTO.getStatus());
    }

    @Test
    public void OrderService_deleteOrder_void(){
        Order newOrder = Order.builder()
                .id(1L)
                .description("TestingOrder")
                .status("In attesa")
                .dueDate(new Date())
                .discount(4L)
                .build();
        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(newOrder));
        dao.deleteOrder(1L);

        // assert exeption thrown : first TODO: implement exception


    }

    @Test
    public void OrderService_getAllOrders_OrderResponse(){
        Page<Order> orders =  mock(Page.class);
        when(orderRepository.findAll(any(Pageable.class))).thenReturn(orders);
        OrderResponse allOrders = dao.getAllOrders(0, 10);
        Assertions.assertNotNull(allOrders);
    }

    @Test
    public void OrderService_searchByCostumerKey_OrderResponse(){

        List<Costumer> costumers = new ArrayList<>();
        costumers.add(Costumer.builder().id(1L).name("CostumerTo[searchKey]").surname("Cognome1").email("renigega@outlook.it").phone(3280119573L).build());
        costumers.add(Costumer.builder().id(1L).name("CostumerTo[searchKey]").surname("Cognome1").email("renigega@outlook.it").phone(3280119573L).build());

        when(costumerRepository.searchCostumer("searchKey")).thenReturn(costumers);


        // call to orderRepository
        List<Order> orders = new ArrayList<>();
        orders.add(Order.builder()
                .id(1L)
                .description("TestingOrder")
                .status("In attesa")
                .dueDate(new Date())
                .discount(4L)
                .costumer(Costumer.builder()
                        .id(1L)
                        .name("CostumerTo[searchKey]")
                        .surname("Cognome1")
                        .email("renigega@outlook.it").build())
                .build());
        orders.add(Order.builder()
                .id(1L)
                .description("TestingOrder")
                .status("In attesa")
                .dueDate(new Date())
                .discount(4L)
                        .costumer(Costumer.builder()
                                .id(1L)
                                .name("CostumerTo[searchKey]")
                                .surname("Cognome1")
                                .email("renigega@outlook.it").build())
                .build());


        when(orderRepository.findByCostumerId(1L)).thenReturn(orders);
        when(orderRepository.findByCostumerId(1L)).thenReturn(orders);

        OrderResponse allOrders = dao.searchByCostumerString("searchKey", 0, 10);

        Assertions.assertNotNull(allOrders);
        // supposing that i find two costumers with the same name
        // and that each costumer has two orders
        // i should get 4 orders
        Assertions.assertEquals(allOrders.getContent().size(), 4);
    }

    @Test
    public void OrderService_getByCostumerId_OrderResponse(){
        List<Order> orders = new ArrayList<>();
        Costumer costumer  = Costumer.builder()
                .id(1L)
                .name("Nome1")
                .surname("Cognome1")
                .email("renigega@outlook.it")
                .build();

        orders.add(Order.builder()
                .id(1L)
                .description("TestingOrder")
                .status("In attesa")
                .dueDate(new Date())
                .discount(4L)
                .costumer(costumer)
                .build());
        orders.add(Order.builder()
                .id(1L)
                .description("TestingOrder")
                .status("In attesa")
                .dueDate(new Date())
                .discount(4L)
                .costumer(costumer)
                .build());



        when(orderRepository.findByCostumerId(1L)).thenReturn(orders);

        OrderResponse allOrders = dao.getByCostumerId(1L, 0, 10);

        Assertions.assertNotNull(allOrders);
        Assertions.assertEquals(allOrders.getContent().size(), 2);
    }


    @Test
    public void OrderService_updateOrder_OrderDTO() {
        Job job = Job.builder()
                .id(1L)
                .name("job1")
                .description("description1")
                .category("category1")
                .price(100)
                .build();
        JobDTO jobDTO = JobDTO.builder()
                .id(1L)
                .name("job1")
                .description("description1")
                .category("category1")
                .price(100)
                .build();

        Costumer costumer  = Costumer.builder()
                .id(1L)
                .name("Nome1")
                .surname("Cognome1")
                .email("renigega@outlook.it")
                .phone(3280119573L)
                .build();

        CostumerDTO costumerDTO = CostumerDTO.builder()
                .id(1L)
                .name("Nome1")
                .surname("Cognome1")
                .email("renigega@outlook.it")
                .phone(3280119573L)
                .build();

        Order order = Order.builder()
                .description("TestingOrder")
                .status("In attesa")
                .dueDate(new Date())
                .discount(4L)
                .build();
        order.addJob(job);
        order.setCostumer(costumer);

        List<JobDTO> jobDTOList = List.of(jobDTO);

        OrderDTO orderDTO = OrderDTO.builder()
                .id(1L)
                .description("TestingOrder")
                .status("In attesa")
                .jobs(jobDTOList)
                .costumer(costumerDTO)
                .dueDate(new Date())
                .discount(4L)
                .build();


        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(costumerRepository.findById(1L)).thenReturn(Optional.of(costumer));
        OrderDTO updatedOrder = dao.updateOrder(orderDTO, 1L);

        Assertions.assertNotNull(updatedOrder);
        Assertions.assertEquals(updatedOrder.getDescription(), orderDTO.getDescription());
    }


}


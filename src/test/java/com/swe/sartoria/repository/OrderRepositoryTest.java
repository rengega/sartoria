package com.swe.sartoria.repository;

import com.swe.sartoria.model.Costumer;
import com.swe.sartoria.model.Job;
import com.swe.sartoria.model.Order;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CostumerRepository costumerRepository;
    @Autowired
    private JobRepository jobRepository;


    @BeforeEach
    public void setUp() {
        Job job1 = Job.builder().name("Job1").description("Description1").category("Pantaloni").price(10).build();
        Job job2 = Job.builder().name("Job2").description("Desc[keyword]ription2").category("Giacca").price(20).build();
        Job job3 = Job.builder().name("Jo[keyword]b3").description("Description3").category("Camicia").price(30).build();

        job1 = jobRepository.save(job1);
        job2 = jobRepository.save(job2);
        job3 = jobRepository.save(job3);

        Costumer costumer1 = Costumer.builder().name("Costumer1").surname("Surname1").email("renigega@outlook.it").phone(1234567890L).build();
        Costumer costumer2 = Costumer.builder().name("Costumer2").surname("Surname2").email("renigega@outlook.it").phone(1234567890L).build();
        Costumer costumer3 = Costumer.builder().name("Costumer3").surname("Surname3").email("renigega@outlook.it").phone(1234567890L).build();

        costumer1 = costumerRepository.save(costumer1);
        costumer2 = costumerRepository.save(costumer2);
        costumer3 = costumerRepository.save(costumer3);

        // Order order1 = Order.builder().costumer(costumer1).dueDate(new Date()).build();
        Order order1 = new Order();
        order1.setCostumer(costumer1);
        order1.setDueDate(new Date());
        order1.addJob(job1);
        order1.addJob(job2);
        order1.addJob(job3);

        Order order2 = new Order();
        order2.setCostumer(costumer2);
        order2.setDueDate(new Date());
        order2.addJob(job1);
        order2.addJob(job2);

        Order order3 = new Order();
        order3.setCostumer(costumer3);
        order3.setDueDate(new Date());
        order3.addJob(job1);

        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);
    }

    @AfterEach
    public void tearDown() {
        orderRepository.deleteAll();
        costumerRepository.deleteAll();
        jobRepository.deleteAll();
    }

    @Test
    public void OrderRepository_saveAndFindById_ReturnSavedOrder() {
        Long i = 1L;
        Job job = jobRepository.findById(i).get();
        Costumer costumer = costumerRepository.findById(i).get();

        while (job == null) {
            i++;
            job = jobRepository.findById(i).get();
        }

        while (costumer == null) {
            i++;
            costumer = costumerRepository.findById(i).get();
        }

        Order order = new Order();
        order.setCostumer(costumer);
        order.setDueDate(new Date());

        order.setDescription("Order inserted by test method");
        order.addJob(job);

        Order savedOrder = orderRepository.save(order);

        Order foundOrder = orderRepository.findById(savedOrder.getId()).get();

        Assertions.assertEquals("Order inserted by test method", foundOrder.getDescription());

    }

    @Test
    public void OrderRepository_deleteById_ReturnAllOrders() {
        orderRepository.deleteById(1L);
        List<Order> orders = orderRepository.findAll();
        Assertions.assertEquals(2, orders.size());
    }

    @Test
    public void OrderRepository_findByCostumerId_ReturnOrders() {
        Long i = 1L;
        Costumer costumer = costumerRepository.findById(i).get();

        while (costumer == null) {
            i++;
            costumer = costumerRepository.findById(i).get();
        }

        List<Order> orders = orderRepository.findByCostumerId(costumer.getId());
        Assertions.assertEquals(1, orders.size());
    }

    @Test
    public void OrderRepository_findAll_ReturnOrders() {
        List<Order> orders = orderRepository.findAll();
        Assertions.assertEquals(3, orders.size());

    }
}

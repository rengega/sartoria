package com.swe.sartoria.service;

import com.swe.sartoria.model.Costumer;
import com.swe.sartoria.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    public void sendMail() {
        Order order = new Order();
        Costumer costumer = new Costumer();
        costumer.setEmail("renigega@outlook.it");
        order.setCostumer(costumer);
        mailService.notifyCostumer(order);
    }
}


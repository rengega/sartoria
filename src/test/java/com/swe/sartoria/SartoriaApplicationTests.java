package com.swe.sartoria;

import com.swe.sartoria.service.impl.CostumerController;
import com.swe.sartoria.service.impl.JobController;
import com.swe.sartoria.service.impl.OrderController;
import com.swe.sartoria.service.mail_service.MailService;
import com.swe.sartoria.model.Costumer;
import com.swe.sartoria.model.Job;
import com.swe.sartoria.model.Order;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@SpringBootTest
class SartoriaApplicationTests {


    // TODO: comment and document to explain everything

    @Nested
    class JobControllerTests {

    }

    @Nested
    class ConsumerControllerTests {

    }

    @Nested
    class OrderControllerTests {
    }
}


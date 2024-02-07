package com.swe.sartoria;

import com.swe.sartoria.buisiness_logic.CostumerConstroller;
import com.swe.sartoria.buisiness_logic.JobController;
import com.swe.sartoria.buisiness_logic.OrderController;
import com.swe.sartoria.dao.DAO;
import com.swe.sartoria.mail_service.MailService;
import com.swe.sartoria.model_domain.Costumer;
import com.swe.sartoria.model_domain.Job;
import com.swe.sartoria.model_domain.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.mock;

@SpringBootTest

class SartoriaApplicationTests {

    @Autowired
    private DAO dao;

    @Autowired
    private MailService mailService;
    @Test
    void contextLoads() {
        CostumerConstroller costumerConstroller = new CostumerConstroller(dao);
        OrderController orderController = new OrderController(dao);
        JobController jobController = new JobController(dao);

        Job job1 = new Job("job1", "desc1", 10);
        Job job2 = new Job("job2", "desc2", 20);
        jobController.addJob(job1);
        jobController.addJob(job2);

        Costumer costumer1 = new Costumer("costumer1", "address1", "renigega@outlook.it", 3280119673L);
        costumerConstroller.addCostumer(costumer1);


        Order od1 = new Order("first order created", "pending", costumer1);
        od1.addJob(job1);
        od1.addJob(job2);

        orderController.SaveOrder(od1);

        Order retrieved = orderController.GetOrder(1);

        retrieved.setStatus("completed");
        orderController.UpdateOrder(retrieved);


    }

    @Test
    void testMailService() {
        mailService.sendMail("renigega@outlook.it", "test", "test");
      }

}

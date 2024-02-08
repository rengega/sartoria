package com.swe.sartoria;

import com.swe.sartoria.buisiness_logic.CostumerConstroller;
import com.swe.sartoria.buisiness_logic.JobController;
import com.swe.sartoria.buisiness_logic.OrderController;
import com.swe.sartoria.dao.DAO;
import com.swe.sartoria.mail_service.MailService;
import com.swe.sartoria.model_domain.Costumer;
import com.swe.sartoria.model_domain.Job;
import com.swe.sartoria.model_domain.Order;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@SpringBootTest

class SartoriaApplicationTests {

    @Autowired
    private DAO dao;


    @Autowired
    private MailService mailService;

    @AfterEach
    void tearDown() {
        JobController jobController = new JobController(dao);
        List<Job> jobs = jobController.getAllJobs();
        for (Job j : jobs) {
            jobController.deleteJob(j);
        }

        CostumerConstroller costumerConstroller = new CostumerConstroller(dao);
        List<Costumer> costumers = costumerConstroller.getAllCostumers();
        for (Costumer c : costumers) {
            costumerConstroller.deleteCostumer(c);
        }

        OrderController orderController = new OrderController(dao);
        List<Order> orders = orderController.getAllOrders();
        for (Order o : orders) {
            orderController.DeleteOrder(o);
        }


    }


    @Test
    void testMailService() {
        mailService.sendMail("renigega@outlook.it", "test", "test");
    }


    @Nested
    class JobControllerTests {
        @BeforeEach
         void setUp() {
            JobController jobController = new JobController(dao);
            CostumerConstroller costumerConstroller = new CostumerConstroller(dao);
            Job job1 = new Job("job1", "descOfJob1.1", 10, "Pantaloni");
            Job job2 = new Job("job1", "descOfJob1.2", 20, "Giacca");
            Job job3 = new Job("job3", "desc3", 30, "Camicia");
            Job job4 = new Job("job4", "desc4", 40, "Gonna");
            Job job5 = new Job("job5", "desc5", 50, "Giacca");


            jobController.addJob(job1);
            jobController.addJob(job2);
            jobController.addJob(job3);
            jobController.addJob(job4);
            jobController.addJob(job5);
        }

        @AfterEach
        void tearDown() {
            JobController jobController = new JobController(dao);
            List<Job> jobs = jobController.getAllJobs();
            for (Job j : jobs) {
                jobController.deleteJob(j.getId());
            }

        }

        @Test
        void testGetJob() {
            JobController jobController = new JobController(dao);
            List<Job> all = jobController.getAllJobs();
            if (all.size() != 0) {
                Job job = all.get(0);
                System.out.println(job.toString());
                Job retrieved = jobController.getJob(job.getId());
                assertEquals(job.getId(), retrieved.getId());
            }

        }

        @Test
        void testGetJobByName() {
            JobController jobController = new JobController(dao);
            List<Job> retrievedByName = jobController.getJobByName("job1");
            System.out.println("Retrieved by name: ");
            for (Job j : retrievedByName) {
                System.out.println(j.toString());
            }
            assertEquals(2, retrievedByName.size());

        }

        @Test
        void testGetJobByCategory() {
            JobController jobController = new JobController(dao);
            List<Job> retrievedByCategory = jobController.getJobByCategory("Giacca");
            Long exp1 = 2L;
            Long exp2 = 5L;
            assertEquals(2, retrievedByCategory.size());
        }

        @Test
        void testGetAllJobs() {
            JobController jobController = new JobController(dao);
            List<Job> retrieved = jobController.getAllJobs();
            assertEquals(5, retrieved.size());
        }

        @Test
        void testAddJob() {
            JobController jobController = new JobController(dao);
            Job job6 = new Job("job6", "desc6", 60, "Giacca");
            jobController.addJob(job6);
            List<Job> retrieved = jobController.getAllJobs();
            assertEquals(6, retrieved.size());
        }

        @Test
        void testDeleteJob() {
            JobController jobController = new JobController(dao);

            List<Job> allJobs = jobController.getAllJobs();

            if (allJobs.size() != 0) {
                System.out.println("List before delete: ");
                for (Job j : allJobs) {
                    System.out.println(j.toString());
                }
                Job selected = allJobs.get(0);
                jobController.deleteJob(selected);
                List<Job> retrieved = jobController.getAllJobs();
                System.out.println("List after delete: ");
                for (Job j : retrieved) {
                    System.out.println(j.toString());
                }
                assertEquals(4, retrieved.size());
            }
        }

        @Test
        void testUpdateJob() {
            JobController jobController = new JobController(dao);
            List<Job> allJobs = jobController.getAllJobs();
            if (allJobs.size() != 0){
                Job selected = allJobs.get(0);
                System.out.println("Selected: ");
                System.out.println(selected.toString());
                selected.setName("NewName");
                jobController.updateJob(selected);
                Job updated = new JobController(dao).getJob(selected.getId());
                System.out.println("Updated: ");
                System.out.println(updated.toString());
                assertEquals("NewName", updated.getName());

            }

        }


    }

    @Nested
    class ConsumerControllerTests {
        @BeforeEach
        void setUp() {
            Costumer costumer1 = new Costumer("Nome1", "Cognome1", "renigega@outlook.it", 3280119673L);
            Costumer costumer2 = new Costumer("Nome1", "Cognome2", "renigega@outlook.it", 3280119673L);
            Costumer costumer3 = new Costumer("Nome3", "Cognome1", "gegareni@gmail.com", 3280119673L);
            Costumer costumer4 = new Costumer("Nome4", "Cognome4", "gegareni@gmail.com", 3280119673L);

            CostumerConstroller costumerConstroller = new CostumerConstroller(dao);

            costumerConstroller.addCostumer(costumer1);
            costumerConstroller.addCostumer(costumer2);
            costumerConstroller.addCostumer(costumer3);
            costumerConstroller.addCostumer(costumer4);

        }

        @AfterEach
        void tearDown() {
            CostumerConstroller costumerConstroller = new CostumerConstroller(dao);
            List<Costumer> costumers = costumerConstroller.getAllCostumers();
            for (Costumer c : costumers) {
                costumerConstroller.deleteCostumer(c);
            }
        }

        @Test
        void testGetAllCostumers() {
            CostumerConstroller costumerConstroller = new CostumerConstroller(dao);
            List<Costumer> retrieved = costumerConstroller.getAllCostumers();
            System.out.println("All Costumers: ");
            for (Costumer c : retrieved) {
                System.out.println(c.toString());
            }
            assertEquals(4, retrieved.size());
        }

        @Test
        void testSearchCostumer() {
            CostumerConstroller costumerConstroller = new CostumerConstroller(dao);
            List<Costumer> retrieved = costumerConstroller.searchCostumer("1");
            System.out.println("Retrieved by search: ");
            for (Costumer c : retrieved) {
                System.out.println(c.toString());
            }
            assertEquals(3, retrieved.size());
        }

        @Test
        void testGetCostumer() {
            CostumerConstroller costumerConstroller = new CostumerConstroller(dao);
            List<Costumer> all = costumerConstroller.getAllCostumers();
            if (all.size() != 0) {
                Costumer costumer = all.get(0);
                System.out.println(costumer.toString());
                Costumer retrieved = costumerConstroller.getCostumer(costumer.getId());
                assertEquals(costumer.getId(), retrieved.getId());
            }
        }

        @Test
        void testAddCostumer() {
            CostumerConstroller costumerConstroller = new CostumerConstroller(dao);
            Costumer costumer5 = new Costumer("AddedCostumerName", "AddedCostumerSurname", "renigega@outlook.it", 3280119673L);
            costumerConstroller.addCostumer(costumer5);
            List<Costumer> retrieved = costumerConstroller.getAllCostumers();
            assertEquals(5, retrieved.size());
        }

        @Test
        void searchCostumer() {
            CostumerConstroller costumerConstroller = new CostumerConstroller(dao);
            List<Costumer> retrieved = costumerConstroller.searchCostumer("1");
            System.out.println("Retrieved by search: ");
            for (Costumer c : retrieved) {
                System.out.println(c.toString());
            }
            assertEquals(3, retrieved.size());
        }

        @Test
        void testDeleteCostumer() {
            CostumerConstroller costumerConstroller = new CostumerConstroller(dao);
            List<Costumer> allCostumers = costumerConstroller.getAllCostumers();
            if (allCostumers.size() != 0) {
                System.out.println("List before delete: ");
                for (Costumer c : allCostumers) {
                    System.out.println(c.toString());
                }
                Costumer selected = allCostumers.get(0);
                costumerConstroller.deleteCostumer(selected);
                List<Costumer> retrieved = costumerConstroller.getAllCostumers();
                System.out.println("List after delete: ");
                for (Costumer c : retrieved) {
                    System.out.println(c.toString());
                }
                assertEquals(3, retrieved.size());
            }
        }

        @Test
        void testUpdateCostumer() {
            CostumerConstroller costumerConstroller = new CostumerConstroller(dao);
            List<Costumer> allCostumers = costumerConstroller.getAllCostumers();
            if (allCostumers.size() != 0){
                Costumer selected = allCostumers.get(0);
                System.out.println("Selected: ");
                System.out.println(selected.toString());
                selected.setName("NewName");
                costumerConstroller.updateConsumer(selected);
                Costumer updated = costumerConstroller.getCostumer(selected.getId());
                System.out.println("Updated: ");
                System.out.println(updated.toString());
                assertEquals("NewName", updated.getName());
            }
        }

    }

    @Nested
    class OrderControllerTests {
        @BeforeEach
        void setUp() {
            CostumerConstroller costumerConstroller = new CostumerConstroller(dao);
            OrderController orderController = new OrderController(dao);
            JobController jobController = new JobController(dao);

            Costumer costumer1 = new Costumer("Nome1", "Cognome1", "renigega@outlook.it", 3280119673L);
            Costumer costumer2 = new Costumer("Nome1", "Cognome2", "renigega@outlook.it", 3280119673L);
            Costumer costumer3 = new Costumer("Nome3", "Cognome1", "gegareni@gmail.com", 3280119673L);
            Costumer costumer4 = new Costumer("Nome4", "Cognome4", "gegareni@gmail.com", 3280119673L);

            costumerConstroller.addCostumer(costumer1);
            costumerConstroller.addCostumer(costumer2);
            costumerConstroller.addCostumer(costumer3);
            costumerConstroller.addCostumer(costumer4);


            Job job1 = new Job("job1", "descOfJob1.1", 10, "Pantaloni");
            Job job2 = new Job("job1", "descOfJob1.2", 20, "Giacca");
            Job job3 = new Job("job3", "desc3", 30, "Camicia");
            Job job4 = new Job("job4", "desc4", 40, "Gonna");
            Job job5 = new Job("job5", "desc5", 50, "Giacca");


            jobController.addJob(job1);
            jobController.addJob(job2);
            jobController.addJob(job3);
            jobController.addJob(job4);
            jobController.addJob(job5);


            Order order1 = new Order("desc1", "In lavorazione", costumer1, new Date());
            Order order2 = new Order("desc2", "In lavorazione", costumer2, new Date());
            Order order3 = new Order("desc3", "In lavorazione", costumer3, new Date());
            Order order4 = new Order("desc4", "In lavorazione", costumer4, new Date());
            Order order5 = new Order("desc5", "In lavorazione", costumer1, new Date());
            Order order6 = new Order("desc6", "In lavorazione", costumer2, new Date());

            order1.addJob(job1);
            order1.addJob(job2);
            order2.addJob(job3);
            order2.addJob(job4);
            order3.addJob(job5);
            order3.addJob(job1);
            order4.addJob(job2);
            order4.addJob(job3);
            order5.addJob(job4);
            order5.addJob(job5);
            order6.addJob(job1);
            order6.addJob(job2);

            orderController.addOrder(order1);
            orderController.addOrder(order2);
            orderController.addOrder(order3);
            orderController.addOrder(order4);
            orderController.addOrder(order5);
            orderController.addOrder(order6);

        }

        @AfterEach
        void tearDown() {
            OrderController orderController = new OrderController(dao);
            List<Order> orders = orderController.getAllOrders();
            for (Order o : orders) {
                orderController.DeleteOrder(o);
            }

            JobController jobController = new JobController(dao);
            List<Job> jobs = jobController.getAllJobs();
            for (Job j : jobs) {
                jobController.deleteJob(j.getId());
            }

            CostumerConstroller costumerConstroller = new CostumerConstroller(dao);
            List<Costumer> costumers = costumerConstroller.getAllCostumers();
            for (Costumer c : costumers) {
                costumerConstroller.deleteCostumer(c);
            }
        }

        @Test
        void testGetAllOrders() {
            OrderController orderController = new OrderController(dao);
            List<Order> retrieved = orderController.getAllOrders();
            System.out.println("All Orders: ");
            for (Order o : retrieved) {
                System.out.println(o.toString());
            }
            assertEquals(6, retrieved.size());
        }

        @Test
        void testGetOrdersByCostumer() {
            OrderController orderController = new OrderController(dao);
            List<Order> all = orderController.getAllOrders();
            List<Order> retrievedByCostumer = orderController.searchOrderByCostumer("Cognome1");
            System.out.println("All Orders: ");
            for (Order o : all) {
                System.out.println(o.toString());
            }

            System.out.println("Retrieved by costumer: ");
            for (Order o : retrievedByCostumer) {
                System.out.println(o.toString());
            }
            assertEquals(3, retrievedByCostumer.size());
        }

        @Test
        void testGetOrder() {
            OrderController orderController = new OrderController(dao);
            List<Order> all = orderController.getAllOrders();
            if (all.size() != 0) {
                Order order = all.get(0);
                System.out.println(order.toString());
                Order retrieved = orderController.GetOrder(order.getId());
                assertEquals(order.getId(), retrieved.getId());
            }
        }

        @Test
        void testAddOrder() {
            OrderController orderController = new OrderController(dao);
            CostumerConstroller costumerConstroller = new CostumerConstroller(dao);
            JobController jobController = new JobController(dao);


            // view and select jobs for "Giacca"
            List<Job> jobs = jobController.getJobByCategory("Giacca");
            System.out.println("Jobs for 'Giacca': ");
            for (Job j : jobs) {
                System.out.println(j.toString());
            }

            Job job1 = jobs.get(0);
            Job job2 = jobs.get(1);


            // view and select costumers with surname "Cognome1"
            List<Costumer> costumers = costumerConstroller.searchCostumer("Cognome1");
            System.out.println("Costumers with surname 'Cognome1': ");
            for (Costumer c : costumers) {
                System.out.println(c.toString());
            }

            Costumer costumer = costumers.get(0);

            // create and add order
            Order order = new Order("desc7", "In lavorazione", costumer, new Date());
            order.addJob(job1);
            order.addJob(job2);

            orderController.addOrder(order);

            List<Order> retrieved = orderController.getAllOrders();
            System.out.println("All Orders: ");
            for (Order o : retrieved) {
                System.out.println(o.toString());
            }

            assertEquals(7, retrieved.size());
        }

        @Test
        void testDeleteOrder() {
            OrderController orderController = new OrderController(dao);
            List<Order> allOrders = orderController.getAllOrders();
            if (allOrders.size() != 0) {
                System.out.println("List before delete: ");
                for (Order o : allOrders) {
                    System.out.println(o.toString());
                }
                Order selected = allOrders.get(0);
                orderController.DeleteOrder(selected);
                List<Order> retrieved = orderController.getAllOrders();
                System.out.println("List after delete: ");
                for (Order o : retrieved) {
                    System.out.println(o.toString());
                }
                assertEquals(5, retrieved.size());
            }
        }

        @Test
        void testUpdateOrder() {
            OrderController orderController = new OrderController(dao);
            JobController jobController = new JobController(dao);
            List<Order> allOrders = orderController.getAllOrders();
            if (allOrders.size() != 0){
                Order selected = allOrders.get(0);
                System.out.println("Selected: ");
                System.out.println(selected.toString());
                selected.setDescription("NewDescription");
                List<Job> jobs = selected.getJobs();
                System.out.println("Jobs before update: ");
                for (Job j : jobs) {
                    System.out.println(j.toString());
                }
                Job job = jobs.get(0);
                selected.removeJob(job);
                List<Job> newJobs = jobController.getAllJobs();
                Job newJob = newJobs.get(3);
                String exptected = newJob.getName();
                selected.addJob(newJob);
                orderController.UpdateOrder(selected);
                Order updated = orderController.GetOrder(selected.getId());
                System.out.println("Updated: ");
                System.out.println(updated.toString());
                List<Job> updatedJobs = updated.getJobs();
                System.out.println("Jobs after update: ");
                for (Job j : updatedJobs) {
                    System.out.println(j.toString());
                }
                assertEquals("NewDescription", updated.getDescription());
                assertEquals(2, updatedJobs.size());
                assertEquals(exptected, updatedJobs.get(1).getName());

            }


        }


    }
            // Costumer Controller Tests

}


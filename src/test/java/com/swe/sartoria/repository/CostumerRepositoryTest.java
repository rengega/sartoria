package com.swe.sartoria.repository;

import com.swe.sartoria.dto.CostumerDTO;
import com.swe.sartoria.model.Costumer;
import lombok.Data;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.AfterTestMethod;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")

public class CostumerRepositoryTest {
    @Autowired
    private CostumerRepository costumerRepository;

    @BeforeEach
    public void setUp() {
            Costumer costumer = Costumer.builder().name("No[NameToSearch]me1").surname("Cognome1").email("renigega@outlook.it").phone(3280119573L).build();
            costumer = costumerRepository.save(costumer);
            costumer = Costumer.builder().name("Nome2").surname("Cognome2").email("renigega@outlook.it").phone(3280119573L).build();
            costumer = costumerRepository.save(costumer);
            costumer = Costumer.builder().name("Nome3").surname("Cognom[NameToSearch]e3").email("renigega@outloo.it").phone(3280119573L).build();
            costumer = costumerRepository.save(costumer);
    }

    @AfterEach
    public void tearDown() {
        costumerRepository.deleteAll();
    }


    @Test
    public void RepositoryTest_save_SavedCostumer() {
        Costumer costumer = Costumer.builder().name("SaveTestName").surname("SaveTestSurname").email("renigega@outlook.it").phone(3280119573L).build();
        costumer = costumerRepository.save(costumer);
        System.out.println(costumer.toString());
        Assertions.assertNotNull(costumer);
    }
    @Test
    public void RepositoryTest_findById_Costumer() {
        Costumer costumer = Costumer.builder().name("SaveTestName").surname("SaveTestSurname").email("renigega@outlook.it").phone(3280119573L).build();
        costumer = costumerRepository.save(costumer);
        System.out.println(costumer.toString());

        Costumer result = costumerRepository.findById(costumer.getId()).orElse(null);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(costumer.getId(), result.getId());
    }

    @Test
    public void  RepositoryTest_deleteCostumer_Void() {
        Costumer costumer = Costumer.builder().name("SaveTestName").surname("SaveTestSurname").email("renigega@outlook.it").phone(3280119573L).build();
        costumer = costumerRepository.save(costumer);
        costumerRepository.delete(costumer);
        Costumer result = costumerRepository.findById(costumer.getId()).orElse(null);
        Assertions.assertNull(result);
    }

    @Test
    public void findAll() {
        List<Costumer> result = costumerRepository.findAll();
        System.out.println(result);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void searchCostumer() {
        List<Costumer> result = costumerRepository.searchCostumer("NameToSearch");
        System.out.println(result);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }

}

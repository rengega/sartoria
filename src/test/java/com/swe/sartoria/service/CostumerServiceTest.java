package com.swe.sartoria.service;

import com.swe.sartoria.dto.CostumerDTO;
import com.swe.sartoria.dto.CostumerResponse;
import com.swe.sartoria.model.Costumer;
import com.swe.sartoria.repository.CostumerRepository;
import com.swe.sartoria.service.impl.CostumerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CostumerServiceTest {
    @Mock
    private CostumerRepository costumerRepository;
    @InjectMocks
    private CostumerServiceImpl costumerService;

    @Test
    public void CostumerService_AddCostumer_ReturnCostumerDTO() {
        Costumer costumer  = Costumer.builder()
                .name("Nome1")
                .surname("Cognome1")
                .email("renigega@outlook.it")
                .phone(3280119573L)
                .build();

        CostumerDTO costumerDTO = CostumerDTO.builder()
                .name("Nome1")
                .surname("Cognome1")
                .email("renigega@outlook.it")
                .phone(3280119573L)
                        .build();

        when(costumerRepository.save(any(Costumer.class))).thenReturn(costumer);

        CostumerDTO costumerDTOResult = costumerService.addCostumer(costumerDTO);

        System.out.println("saved object: " );
        System.out.println(costumerDTO.toString());
        System.out.println("result object: " );
        System.out.println(costumerDTOResult.toString());

        assertEquals(costumerDTO, costumerDTOResult);


    }

    @Test
    public void CostumerService_GetAllCostumers_ReturnCostumerResponse() {
        Page<Costumer> costumers =  mock(Page.class);
        System.out.println("costumers: " + costumers);
        when(costumerRepository.findAll(any(Pageable.class))).thenReturn(costumers);
        CostumerResponse costumerResponse = costumerService.getAllCostumers(0, 10);
        Assertions.assertNotNull(costumerResponse);
    }



    @Test
    public void CostumerService_DeleteCostumer_ReturnVoid() {
        long costumerId = 0;
        Costumer costumer  = Costumer.builder()
                .name("Nome1")
                .surname("Cognome1")
                .email("renigega@outlook.it")
                .phone(3280119573L)
                .build();

        when(costumerRepository.findById(costumerId)).thenReturn(Optional.ofNullable(costumer));
        doNothing().when(costumerRepository).delete(costumer);

        assertAll(() -> costumerService.deleteCostumer(costumerId));

    }

    @Test
    public void CostumerService_FindCostumerById_ReturnCostumerDTO() {
        long costumerId = 1;
        Costumer costumer  = Costumer.builder()
                .name("Nome1")
                .surname("Cognome1")
                .email("renigega@outlook.it")
                .phone(3280119573L)
                .build();
        when(costumerRepository.findById(costumerId)).thenReturn(Optional.ofNullable(costumer));

        CostumerDTO costumerReturn = costumerService.findCostumerById(costumerId);

        Assertions.assertNotNull(costumerReturn);
    }

    @Test
    public void CostumerService_UpdateCostumer_ReturnCostumerDTO() {

    }

    @Test
    public void CostumerService_SearchByCostumerString_ReturnCostumerResponse() {
    }


}

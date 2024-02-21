package com.swe.sartoria.service;

import com.swe.sartoria.dto.CostumerDTO;
import com.swe.sartoria.dto.CostumerResponse;
import com.swe.sartoria.model.Costumer;
import com.swe.sartoria.repository.CostumerRepository;
import com.swe.sartoria.repository.JobRepository;
import com.swe.sartoria.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
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
    private DAO dao;

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

        CostumerDTO costumerDTOResult = dao.addCostumer(costumerDTO);

        System.out.println("saved object: " );
        System.out.println(costumerDTO.toString());
        System.out.println("result object: " );
        System.out.println(costumerDTOResult.toString());

        assertEquals(costumerDTO, costumerDTOResult);


    }

    @Test
    public void CostumerService_GetAllCostumers_ReturnCostumerResponse() {
        Page<Costumer> costumers =  mock(Page.class);
        when(costumerRepository.findAll(any(Pageable.class))).thenReturn(costumers);
        CostumerResponse costumerResponse = dao.getAllCostumers(0, 10);
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

        assertAll(() -> dao.deleteCostumer(costumerId));

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

        CostumerDTO costumerReturn = dao.findCostumerById(costumerId);

        Assertions.assertNotNull(costumerReturn);
    }

    @Test
    public void CostumerService_UpdateCostumer_ReturnCostumerDTO() {
        long costumerId = 1L;
        Costumer costumer  = Costumer.builder()
                .name("Nome1")
                .surname("Cognome1")
                .email("renigega@outlook.it")
                .phone(3280119573L)
                .build();
        CostumerDTO costumerDTO = CostumerDTO.builder()
                .name("Nome1")
                .surname("Cognome1")
                .email("renigega@outlok.it")
                .phone(3280119573L)
                .build();
        costumer.setId(costumerId);
        costumerDTO.setId(costumerId);

        when(costumerRepository.findById(costumerId)).thenReturn(Optional.ofNullable(costumer));
        when(costumerRepository.save(costumer)).thenReturn(costumer);

        CostumerDTO updateReturn = dao.updateCostumer(costumerDTO, costumerId);

        Assertions.assertNotNull(updateReturn);
    }



    @Test
    public void CostumerService_SearchByCostumerString_ReturnCostumerResponse() {
        Page<Costumer> costumers =  mock(Page.class);
        List<Costumer> content = mock(List.class);
        String searchKey = "searchKey";
        when(costumerRepository.searchCostumer(searchKey)).thenReturn(content);
        CostumerResponse costumerResponse = dao.searchCostumer(searchKey, 0, 10);
        Assertions.assertNotNull(costumerResponse);
    }


}

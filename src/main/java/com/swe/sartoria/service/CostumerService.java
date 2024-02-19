package com.swe.sartoria.service;

import com.swe.sartoria.dto.CostumerDTO;
import com.swe.sartoria.dto.CostumerResponse;
import com.swe.sartoria.model.Costumer;

public interface CostumerService {
    CostumerDTO addCostumer(CostumerDTO costumerDTO);
    CostumerResponse searchCostumer(String search, int pageNo, int pageSize);
    CostumerDTO findCostumerById(long id);

    CostumerDTO updateCostumer(CostumerDTO costumerDTO, long id);

    void deleteCostumer(long id);

    CostumerResponse getAllCostumers(int pageNo, int pageSize);

    CostumerDTO getCostumerById(long id);

    Costumer mapToEntity(CostumerDTO costumerDTO);
    CostumerDTO mapToDTO(Costumer costumer);
}

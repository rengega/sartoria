package com.swe.sartoria.service;

import com.swe.sartoria.dto.CostumerDTO;
import com.swe.sartoria.dto.CostumerResponse;

public interface CostumerService {
    CostumerDTO addCostumer(CostumerDTO costumerDTO);
    CostumerResponse searchCostumer(String search);
    CostumerDTO findCostumerById(long id);

    CostumerDTO updateCostumer(CostumerDTO costumerDTO, long id);

    void deleteCostumer(long id);

    CostumerResponse getAllCostumers(int pageNo, int pageSize);
}

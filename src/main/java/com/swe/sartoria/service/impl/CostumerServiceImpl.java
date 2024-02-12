package com.swe.sartoria.service.impl;

// TODO: Exception handling

import com.swe.sartoria.dto.CostumerDTO;
import com.swe.sartoria.dto.CostumerResponse;
import com.swe.sartoria.model.Costumer;
import com.swe.sartoria.repository.CostumerRepository;
import com.swe.sartoria.service.CostumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CostumerServiceImpl implements CostumerService {
    private CostumerRepository costumerRepository;
    @Autowired
    public CostumerServiceImpl (CostumerRepository costumerRepository) {
        this.costumerRepository = costumerRepository;
    }

    @Override
    public CostumerDTO addCostumer(CostumerDTO costumerDTO) {
        Costumer newCostumer = mapToEntity(costumerDTO);

        newCostumer = costumerRepository.save(newCostumer);

        CostumerDTO responseDTO = mapToDTO(newCostumer);

        return responseDTO;
    }

    @Override
    public CostumerDTO findCostumerById(long id) {
        Costumer costumer = costumerRepository.findById(id).orElse(null);
        if (costumer == null) {
            return null;
        }

        CostumerDTO costumerDTO = new CostumerDTO();
        costumerDTO.setId(costumer.getId());
        costumerDTO.setName(costumer.getName());
        costumerDTO.setSurname(costumer.getSurname());
        costumerDTO.setEmail(costumer.getEmail());
        costumerDTO.setPhone(costumer.getPhone());

        return costumerDTO;
    }

    @Override
    public CostumerDTO updateCostumer(CostumerDTO costumerDTO, long id) {
        Costumer costumer = costumerRepository.findById(id).orElse(null);
        if (costumer == null) {
            return null;
        }

        costumer.setName(costumerDTO.getName());
        costumer.setSurname(costumerDTO.getSurname());
        costumer.setEmail(costumerDTO.getEmail());
        costumer.setPhone(costumerDTO.getPhone());

        costumer = costumerRepository.save(costumer);

        CostumerDTO responseDTO = new CostumerDTO();
        responseDTO.setId(costumer.getId());
        responseDTO.setName(costumer.getName());
        responseDTO.setSurname(costumer.getSurname());
        responseDTO.setEmail(costumer.getEmail());
        responseDTO.setPhone(costumer.getPhone());

        return responseDTO;
    }

    @Override
    public void deleteCostumer(long id) {
        Costumer costumer = costumerRepository.findById(id).orElse(null);
        // TODO: Exception handling instead of this
        if (costumer != null) {
            costumerRepository.delete(costumer);
        }
    }

    @Override
    public CostumerResponse getAllCostumers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Costumer> costumerPage = costumerRepository.findAll(pageable);
        List<Costumer> costumerList = costumerPage.getContent();
        List<CostumerDTO> costumerDTOList = costumerList.stream().map(c -> mapToDTO(c)).collect(Collectors.toList());

        CostumerResponse costumerResponse = new CostumerResponse();
        costumerResponse.setContent(costumerDTOList);
        costumerResponse.setPageNo(costumerPage.getNumber());
        costumerResponse.setPageSize(costumerPage.getSize());
        costumerResponse.setTotalElements(costumerPage.getTotalElements());
        costumerResponse.setTotalPages(costumerPage.getTotalPages());
        costumerResponse.setLast(costumerPage.isLast());

        return costumerResponse;
    }

    @Override
    public CostumerResponse searchCostumer(String search){
        List<Costumer> costumerList = costumerRepository.searchCostumer(search);
        List<CostumerDTO> costumerDTOList = costumerList.stream().map(c -> mapToDTO(c)).collect(Collectors.toList());

        CostumerResponse costumerResponse = new CostumerResponse();
        costumerResponse.setContent(costumerDTOList);
        costumerResponse.setPageNo(0);
        costumerResponse.setPageSize(costumerDTOList.size());
        costumerResponse.setTotalElements(costumerDTOList.size());
        costumerResponse.setTotalPages(1);
        costumerResponse.setLast(true);

        return costumerResponse;
    }

    private CostumerDTO mapToDTO(Costumer costumer) {
        CostumerDTO costumerDTO = new CostumerDTO();
        costumerDTO.setId(costumer.getId());
        costumerDTO.setName(costumer.getName());
        costumerDTO.setSurname(costumer.getSurname());
        costumerDTO.setEmail(costumer.getEmail());
        costumerDTO.setPhone(costumer.getPhone());
        return costumerDTO;
    }

    private Costumer mapToEntity(CostumerDTO costumerDTO) {
        Costumer costumer = new Costumer();
        costumer.setId(costumerDTO.getId());
        costumer.setName(costumerDTO.getName());
        costumer.setSurname(costumerDTO.getSurname());
        costumer.setEmail(costumerDTO.getEmail());
        costumer.setPhone(costumerDTO.getPhone());
        return costumer;
    }
}

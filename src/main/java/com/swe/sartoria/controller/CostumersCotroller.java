package com.swe.sartoria.controller;


import com.swe.sartoria.dto.CostumerDTO;
import com.swe.sartoria.dto.CostumerResponse;
import com.swe.sartoria.model.Costumer;
import com.swe.sartoria.service.DAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/costumers")
public class CostumersCotroller {
    private final DAO dao;

    @Autowired
    public CostumersCotroller(DAO dao) {
        this.dao = dao;
    }

    private CostumerDTO mapCostumerToDTO(Costumer costumer) {
        CostumerDTO costumerDTO = new CostumerDTO();
        costumerDTO.setId(costumer.getId());
        costumerDTO.setName(costumer.getName());
        costumerDTO.setSurname(costumer.getSurname());
        costumerDTO.setEmail(costumer.getEmail());
        costumerDTO.setPhone(costumer.getPhone());
        return costumerDTO;
    }

    private Costumer mapCostumerToEntity(CostumerDTO costumerDTO) {
        Costumer costumer = new Costumer();
        costumer.setId(costumerDTO.getId());
        costumer.setName(costumerDTO.getName());
        costumer.setSurname(costumerDTO.getSurname());
        costumer.setEmail(costumerDTO.getEmail());
        costumer.setPhone(costumerDTO.getPhone());
        return costumer;
    }


    @GetMapping("getCostumerById/{id}")
    public ResponseEntity<CostumerDTO> getCostumerById(@PathVariable Long id){
        Costumer costumer = dao.getCostumerById(id);
        CostumerDTO response = mapCostumerToDTO(costumer);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllCostumers")
    public ResponseEntity<CostumerResponse> getAllCostumers(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    )
    {

        // Retrieve from database with DAO
        List<Costumer> listOfAllCostumers = dao.getAllCostumers();
        if (listOfAllCostumers.size() == 0) {
            return ResponseEntity.ok(new CostumerResponse());
        }

        // Map to DTO
        List<CostumerDTO> costumerDTOS = new ArrayList<>();
        for (Costumer costumer : listOfAllCostumers) {
            costumerDTOS.add(mapCostumerToDTO(costumer));
        }

        // Map to Response

        CostumerResponse response = new CostumerResponse();
        response.setContent(costumerDTOS);
        response.setPageNo(pageNo);
        response.setPageSize(pageSize);
        response.setTotalElements(listOfAllCostumers.size());
        response.setTotalPages(listOfAllCostumers.size() / pageSize);
        response.setLast(listOfAllCostumers.size() <= pageSize);

        // Return
        return ResponseEntity.ok(response);
    }

    @GetMapping("/searchCostumer/{search}")
    public ResponseEntity<CostumerResponse> searchCostumers(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @PathVariable String search

    )
    {

        // Retrieve from database with DAO
        List<Costumer> listOfAllCostumers = dao.searchCostumer(search);
        if (listOfAllCostumers.size() == 0) {
            return ResponseEntity.ok(new CostumerResponse());
        }

        // Map to DTO
        List<CostumerDTO> costumerDTOS = new ArrayList<>();
        for (Costumer costumer : listOfAllCostumers) {
            costumerDTOS.add(mapCostumerToDTO(costumer));
        }

        // Map to Response

        CostumerResponse response = new CostumerResponse();
        response.setContent(costumerDTOS);
        response.setPageNo(pageNo);
        response.setPageSize(pageSize);
        response.setTotalElements(listOfAllCostumers.size());
        response.setTotalPages(listOfAllCostumers.size() / pageSize);
        response.setLast(listOfAllCostumers.size() <= pageSize);

        // Return
        return ResponseEntity.ok(response);
    }

    @PostMapping("/addCostumer")
    public ResponseEntity<CostumerDTO> addCostumer(@RequestBody CostumerDTO costumerDto){
        Costumer toSave = mapCostumerToEntity(costumerDto);
        Costumer saved = dao.addCostumer(toSave);
        return ResponseEntity.ok(mapCostumerToDTO(saved));
    }

    @PutMapping("/updateCostumer")
    public ResponseEntity<CostumerDTO> updateCostumer(@RequestBody CostumerDTO costumerDto){
        Costumer toUpdate = mapCostumerToEntity(costumerDto);
        Costumer updated = dao.updateCostumer(toUpdate, toUpdate.getId());
        return ResponseEntity.ok(mapCostumerToDTO(updated));
    }

    @DeleteMapping("/deleteCostumer/{id}")
    public ResponseEntity<String> deleteCostumer(@PathVariable Long id){
        dao.deleteCostumer(id);
        return new ResponseEntity<>("Costumer deleted successfully", HttpStatus.OK);
    }
}

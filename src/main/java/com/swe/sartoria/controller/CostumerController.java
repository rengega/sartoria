package com.swe.sartoria.controller;

import com.swe.sartoria.dto.CostumerDTO;
import com.swe.sartoria.dto.CostumerResponse;
import com.swe.sartoria.service.DAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/costumer")
public class CostumerController {
    private final DAO dao;
    @Autowired
    public CostumerController(DAO dao) {
        this.dao = dao;
    }
    @GetMapping("/testMethod")
    public void controllerHit(){
        System.out.println("Consumer controller hit");
    }

    @GetMapping("/getAllCostumers")
    public ResponseEntity<CostumerResponse> getAllCostumers(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    )
    {
        return ResponseEntity.ok(dao.getAllCostumers(pageNo, pageSize));
    }

    @GetMapping("/getCostumerById/{id}")
    public ResponseEntity<CostumerDTO> getCostumerById(@PathVariable int id){
        return ResponseEntity.ok(dao.getCostumerById(id));
    }

    @GetMapping("/searchCostumer/{search}")
    public ResponseEntity<CostumerResponse> searchCostumer(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @PathVariable String search
    )
    {
        return ResponseEntity.ok(dao.searchCostumer(search, pageNo, pageSize));
    }
    @PostMapping("/addCostumer")
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public ResponseEntity<CostumerDTO> addCostumer(@RequestBody CostumerDTO costumerDto){
        return new ResponseEntity<>(dao.addCostumer(costumerDto), HttpStatus.CREATED);
    }

    @PutMapping("/updateCostumer")
    public ResponseEntity<CostumerDTO> updateCostumer(@RequestBody CostumerDTO costumerDto){
        return new ResponseEntity<>(dao.updateCostumer(costumerDto, costumerDto.getId()), HttpStatus.OK);
    }

    @DeleteMapping("/deleteCostumer/{id}")
    public ResponseEntity<String> deleteCostumer(@PathVariable int id){
        dao.deleteCostumer(id);
        return new ResponseEntity<>("Costumer deleted successfully", HttpStatus.OK);
    }

}

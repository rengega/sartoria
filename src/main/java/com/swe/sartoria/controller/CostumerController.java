package com.swe.sartoria.controller;

import com.swe.sartoria.dto.CostumerDTO;
import com.swe.sartoria.dto.CostumerResponse;
import com.swe.sartoria.service.CostumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/costumer")
public class CostumerController {
    private final CostumerService costumerService;
    @Autowired
    public CostumerController(CostumerService costumerService) {
        this.costumerService = costumerService;
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
        return ResponseEntity.ok(costumerService.getAllCostumers(pageNo, pageSize));
    }

    @GetMapping("/searchCostumer/{search}")
    public ResponseEntity<CostumerResponse> searchCostumer(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @PathVariable String search
    )
    {
        return ResponseEntity.ok(costumerService.searchCostumer(search, pageNo, pageSize));
    }
    @PostMapping("/addCostumer")
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public ResponseEntity<CostumerDTO> addCostumer(@RequestBody CostumerDTO costumerDto){
        return new ResponseEntity<>(costumerService.addCostumer(costumerDto), HttpStatus.CREATED);
    }

    @PutMapping("/updateCostumer")
    public ResponseEntity<CostumerDTO> updateCostumer(@RequestBody CostumerDTO costumerDto){
        return new ResponseEntity<>(costumerService.updateCostumer(costumerDto, costumerDto.getId()), HttpStatus.OK);
    }

    @DeleteMapping("/deleteCostumer/{id}")
    public ResponseEntity<String> deleteCostumer(@PathVariable int id){
        costumerService.deleteCostumer(id);
        return new ResponseEntity<>("Costumer deleted successfully", HttpStatus.OK);
    }

}

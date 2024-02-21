package com.swe.sartoria.controller;

import com.swe.sartoria.dto.JobDTO;
import com.swe.sartoria.dto.JobResponse;
import com.swe.sartoria.service.DAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    private final DAO dao;

    @Autowired
    public JobController(DAO dao) {
        this.dao = dao;
    }

    @GetMapping("/testMethod")
    public void controllerHit(){
        System.out.println("Jobs controller hit");
    }

    @GetMapping("/getAllJobs")
    public ResponseEntity<JobResponse> getAllJobs(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    )
    {
        return ResponseEntity.ok(dao.getAllJobs(pageNo, pageSize));
    }

    @GetMapping("/getJobsByCategory/{category}")
    public ResponseEntity<JobResponse> getJobsByCategory(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @PathVariable String category
    )
    {
        return ResponseEntity.ok(dao.getJobsByCategory(category));
    }

    @GetMapping("/searchJobs/{search}")
    public ResponseEntity<JobResponse> searchJob(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @PathVariable String search
    )
    {
        return ResponseEntity.ok(dao.searchJobs(search, pageNo, pageSize));
    }

    @PostMapping("/addJob")
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public ResponseEntity<JobDTO> addJob(@RequestBody JobDTO jobDto){
        return new ResponseEntity<>(dao.addJob(jobDto), HttpStatus.CREATED);
    }

    @PutMapping("/updateJob")
    public ResponseEntity<JobDTO> updateJob(@RequestBody JobDTO jobDto){
        return new ResponseEntity<>(dao.updateJob(jobDto, jobDto.getId()), HttpStatus.OK);
    }

    @DeleteMapping("/deleteJob/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id){
        dao.deleteJobById(id);
        return new ResponseEntity<>("Job deleted successfully", HttpStatus.OK);
    }


}

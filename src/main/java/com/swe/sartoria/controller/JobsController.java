package com.swe.sartoria.controller;


import com.swe.sartoria.dto.JobDTO;
import com.swe.sartoria.dto.JobResponse;
import com.swe.sartoria.model.Job;
import com.swe.sartoria.service.DAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/costumers")
public class JobsController {
    private final DAO dao;

    @Autowired
    public JobsController(DAO dao) {
        this.dao = dao;
    }

    private Job mapJobToEntity(JobDTO dto){
        Job newJob = new Job();
        newJob.setId(dto.getId());
        newJob.setName(dto.getName());
        newJob.setDescription(dto.getDescription());
        newJob.setCategory(dto.getCategory());
        newJob.setPrice(dto.getPrice());
        return newJob;
    }


    private JobDTO mapJobToDTO(Job job){
        JobDTO jobDTO = new JobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setName(job.getName());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setCategory(job.getCategory());
        jobDTO.setPrice(job.getPrice());
        return jobDTO;
    }


    @GetMapping("getJobById/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long id){
        Job job = dao.getJobById(id);
        JobDTO jobDTO = mapJobToDTO(job);
        return ResponseEntity.ok(jobDTO);
    }

    @GetMapping("/getAllJobs")
    public ResponseEntity<JobResponse> getAllJobs(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    )
    {

        // Retrieve from database with DAO
        List<Job> listOfAllJobs = dao.getAllJobs();
        if (listOfAllJobs.size() == 0) {
            return ResponseEntity.ok(new JobResponse());
        }

        // Map to DTO
        List<JobDTO> jobDTOS = new ArrayList<>();
        for (Job job : listOfAllJobs) {
            jobDTOS.add(mapJobToDTO(job));
        }

        // Map to Response

        JobResponse response = new JobResponse();
        response.setContent(jobDTOS);
        response.setPageNo(pageNo);
        response.setPageSize(pageSize);
        response.setTotalElements(listOfAllJobs.size());
        response.setTotalPages(listOfAllJobs.size() / pageSize);
        response.setLast(listOfAllJobs.size() <= pageSize);

        // Return
        return ResponseEntity.ok(response);
    }

    @GetMapping("/searchJob/{search}")
    public ResponseEntity<JobResponse> searchCostumers(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @PathVariable String search

    )
    {

        // Retrieve from database with DAO
        List<Job> listOfAllJobs = dao.searchJobs(search);
        if (listOfAllJobs.size() == 0) {
            return ResponseEntity.ok(new JobResponse());
        }

        // Map to DTO
        List<JobDTO> jobDTOS = new ArrayList<>();
        for (Job job : listOfAllJobs) {
            jobDTOS.add(mapJobToDTO(job));
        }

        // Map to Response

        JobResponse response = new JobResponse();
        response.setContent(jobDTOS);
        response.setPageNo(pageNo);
        response.setPageSize(pageSize);
        response.setTotalElements(listOfAllJobs.size());
        response.setTotalPages(listOfAllJobs.size() / pageSize);
        response.setLast(listOfAllJobs.size() <= pageSize);

        // Return
        return ResponseEntity.ok(response);
    }

    @PostMapping("/addJob")
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public ResponseEntity<JobDTO> addJob(@RequestBody JobDTO jobDto){
        Job newJob = mapJobToEntity(jobDto);
        Job added = dao.addJob(newJob);
        return ResponseEntity.ok(mapJobToDTO(added));
    }

    @PutMapping("/updateJob")
    public ResponseEntity<JobDTO> updateJob(@RequestBody JobDTO jobDto){
        Job toUpdate = mapJobToEntity(jobDto);
        Job updated = dao.updateJob(toUpdate, toUpdate.getId());
        return ResponseEntity.ok(mapJobToDTO(updated));

    }



    @DeleteMapping("/deleteJob/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id){
        dao.deleteJobById(id);
        return new ResponseEntity<>("Job deleted successfully", HttpStatus.OK);
    }
}

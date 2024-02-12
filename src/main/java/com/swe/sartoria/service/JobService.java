package com.swe.sartoria.service;

import com.swe.sartoria.dto.JobDTO;
import com.swe.sartoria.dto.JobResponse;

public interface JobService {
    JobDTO addJob(JobDTO jobDTO);
    JobDTO getJobById(Long id);
    JobResponse getAllJobs(int pageNo, int pageSize);
    JobResponse getJobsByCategory(String category);
    JobResponse searchJobs(String search);
    JobDTO updateJob(JobDTO jobDTO, Long id);
    void deleteJob(Long id);
}



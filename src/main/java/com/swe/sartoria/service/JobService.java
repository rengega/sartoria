package com.swe.sartoria.service;

import com.swe.sartoria.dto.JobDTO;
import com.swe.sartoria.dto.JobResponse;
import com.swe.sartoria.model.Job;

public interface JobService {
    JobDTO addJob(JobDTO jobDTO);
    JobDTO getJobById(Long id);
    JobResponse getAllJobs(int pageNo, int pageSize);
    JobResponse getJobsByCategory(String category);
    JobResponse searchJobs(String search);
    JobDTO updateJob(JobDTO jobDTO, Long id);
    void deleteJobById(Long id);

    JobDTO mapToDTO(Job job);
    Job mapToEntity(JobDTO jobDTO);
}



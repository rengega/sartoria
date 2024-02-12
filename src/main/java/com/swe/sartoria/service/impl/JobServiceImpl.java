package com.swe.sartoria.service.impl;

import com.swe.sartoria.dto.JobDTO;
import com.swe.sartoria.dto.JobResponse;
import com.swe.sartoria.model.Job;
import com.swe.sartoria.repository.JobRepository;
import com.swe.sartoria.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// TODO: Exception handling
@Service
public class JobServiceImpl implements JobService {
    @Autowired
    private JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public JobDTO addJob(JobDTO jobDTO) {
        Job newJob = mapToEntity(jobDTO);
        Job savedJob = jobRepository.save(newJob);
        return mapToDto(savedJob);


    }


    @Override
    public JobDTO getJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        return mapToDto(job);
    }

    @Override
    public JobDTO updateJob(JobDTO jobDTO, Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        if (job == null) {
            return null;
        }

        job.setName(jobDTO.getName());
        job.setDescription(jobDTO.getDescription());
        job.setCategory(jobDTO.getCategory());
        job.setPrice(jobDTO.getPrice());

        Job updatedJob = jobRepository.save(job);
        return mapToDto(updatedJob);
    }

    @Override
    public JobResponse getAllJobs(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Job> jobs = jobRepository.findAll(pageable);
        List<Job> listOfJobs = jobs.getContent();
        List<JobDTO> content = listOfJobs.stream().map(j -> mapToDto(j)).collect(Collectors.toList());

        JobResponse jobResponse = new JobResponse();
        jobResponse.setContent(content);
        jobResponse.setPageNo(jobs.getNumber());
        jobResponse.setPageSize(jobs.getSize());
        jobResponse.setTotalElements(jobs.getTotalElements());
        jobResponse.setTotalPages(jobs.getTotalPages());
        jobResponse.setLast(jobs.isLast());

        return jobResponse;
    }

    @Override
    public void deleteJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        if (job != null) {
            jobRepository.delete(job);
        }
    }

    @Override
    public JobResponse getJobsByCategory(String category) {
        List<Job> jobs = jobRepository.findByCategory(category);
        List<JobDTO> content = jobs.stream().map(j -> mapToDto(j)).collect(Collectors.toList());

        JobResponse jobResponse = new JobResponse();
        jobResponse.setContent(content);
        jobResponse.setPageNo(0);
        jobResponse.setPageSize(content.size());
        jobResponse.setTotalElements(content.size());
        jobResponse.setTotalPages(1);
        jobResponse.setLast(true);

        return jobResponse;
    }

    @Override
    public JobResponse searchJobs(String key){
        List<Job> jobs = jobRepository.searchJob(key);
        List<JobDTO> content = jobs.stream().map(j -> mapToDto(j)).collect(Collectors.toList());

        JobResponse jobResponse = new JobResponse();
        jobResponse.setContent(content);
        jobResponse.setPageNo(0);
        jobResponse.setPageSize(content.size());
        jobResponse.setTotalElements(content.size());
        jobResponse.setTotalPages(1);
        jobResponse.setLast(true);

        return jobResponse;
    }

    @Override
    public Job mapToEntity(JobDTO dto){
        Job newJob = new Job();
        newJob.setId(dto.getId());
        newJob.setName(dto.getName());
        newJob.setDescription(dto.getDescription());
        newJob.setCategory(dto.getCategory());
        newJob.setPrice(dto.getPrice());
        return newJob;
    }


    @Override
    public JobDTO mapToDto(Job job){
        JobDTO jobDTO = new JobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setName(job.getName());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setCategory(job.getCategory());
        jobDTO.setPrice(job.getPrice());
        return jobDTO;
    }
}

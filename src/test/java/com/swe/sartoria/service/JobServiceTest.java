package com.swe.sartoria.service;

import com.swe.sartoria.dto.CostumerResponse;
import com.swe.sartoria.dto.JobDTO;
import com.swe.sartoria.dto.JobResponse;
import com.swe.sartoria.model.Costumer;
import com.swe.sartoria.model.Job;
import com.swe.sartoria.repository.JobRepository;
import com.swe.sartoria.service.impl.JobServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class JobServiceTest {
    @Mock
    private JobRepository jobRepository;
    @InjectMocks
    private JobServiceImpl jobService;

    @Test
    public void JobService_AddJob_ReturnJobDTO() {
        Job job = Job.builder()
                .name("job1")
                .description("description1")
                .category("category1")
                .price(100)
                .build();
        JobDTO jobDTO = JobDTO.builder()
                .name("job1")
                .description("description1")
                .category("category1")
                .price(100)
                .build();
        when(jobRepository.save(any(Job.class))).thenReturn(job);
        JobDTO jobDTOResult = jobService.addJob(jobDTO);
        System.out.println("saved object: " );
        System.out.println(jobDTO.toString());
    }

    @Test
    public void JobService_GetAllJobs_ReturnsJobResponse() {
        Page<Job> jobs =  mock(Page.class);
        when(jobRepository.findAll(any(Pageable.class))).thenReturn(jobs);
        JobResponse jobResponse = jobService.getAllJobs(0, 10);
        Assertions.assertNotNull(jobResponse);
    }

    @Test
    public void JobService_SearchJobs_ReturnsJobResponse() {
        Page<Job> jobs =  mock(Page.class);
        List<Job> jobsList = mock(List.class);
        String searchKey = "searchKey";
        when(jobRepository.searchJob(searchKey)).thenReturn(jobsList);
        JobResponse jobResponse = jobService.searchJobs(searchKey);
        Assertions.assertNotNull(jobResponse);
    }

    @Test
    public void JobService_GetJobsByCategory_ReturnsJobResponse() {
        Page<Job> jobs =  mock(Page.class);
        List<Job> jobsList = mock(List.class);
        String category = "category";
        when(jobRepository.findByCategory(category)).thenReturn(jobsList);
        JobResponse jobResponse = jobService.getJobsByCategory(category);
        Assertions.assertNotNull(jobResponse);
    }

    @Test
    public void JobService_GetJobById_ReturnsJobDTO() {
        long jobId = 1;
        Job job = Job.builder().id(1).name("job1").description("description1").category("category1").price(100).build();
        when(jobRepository.findById(jobId)).thenReturn(java.util.Optional.ofNullable(job));
        JobDTO jobReturn = jobService.getJobById(jobId);
        Assertions.assertNotNull(jobReturn);
    }

    @Test
    public void JobService_UpdateJob_ReturnsJobDTO() {
        long jobId = 1;
        Job job = Job.builder().id(1).name("job1").description("description1").category("category1").price(100).build();
        JobDTO jobDTO = JobDTO.builder().id(1).name("job1").description("description1").category("category1").price(100).build();
        when(jobRepository.findById(jobId)).thenReturn(java.util.Optional.ofNullable(job));
        when(jobRepository.save(job)).thenReturn(job);
        JobDTO jobReturn = jobService.updateJob(jobDTO, jobId);
        Assertions.assertNotNull(jobReturn);
    }

    @Test
    public void JobService_DeleteJob_ReturnsVoid() {
        long jobId = 1;
        Job job = Job.builder().id(1).name("job1").description("description1").category("category1").price(100).build();
        when(jobRepository.findById(jobId)).thenReturn(java.util.Optional.ofNullable(job));
        doNothing().when(jobRepository).delete(job);
        jobService.deleteJobById(jobId);
    }

}

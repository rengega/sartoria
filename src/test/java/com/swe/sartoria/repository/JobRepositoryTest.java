package com.swe.sartoria.repository;


import com.swe.sartoria.model.Job;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

public class JobRepositoryTest {
    @Autowired
    private JobRepository jobRepository;

    @BeforeEach
    public void setUp() {
        Job job1 = Job.builder().name("Job1").description("Description1").category("Pantaloni").price(10).build();
        Job job2 = Job.builder().name("Job2").description("Desc[keyword]ription2").category("Giacca").price(20).build();
        Job job3 = Job.builder().name("Jo[keyword]b3").description("Description3").category("Camicia").price(30).build();

        jobRepository.save(job1);
        jobRepository.save(job2);
        jobRepository.save(job3);
    }

    @AfterEach
    public void tearDown() {
        jobRepository.deleteAll();
    }

    @Test
    public void RepositoryTest_save_SavedJob() {
        Job job = Job.builder().name("Job4").description("Description4").category("Cappotto").price(40).build();
        job = jobRepository.save(job);
        System.out.println(job.toString());
        Assertions.assertNotNull(job);
    }

    @Test
    public void RepositoryTest_findById_Job() {
        Job job = Job.builder().name("Job5").description("Description5").category("Cappotto").price(50).build();
        job = jobRepository.save(job);
        System.out.println(job.toString());

        Job result = jobRepository.findById(job.getId()).orElse(null);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(job.getId(), result.getId());
    }

    @Test
    public void RepositoryTest_deleteJobById_Void() {
        Job job = Job.builder().name("Job6").description("Description6").category("Cappotto").price(60).build();
        job = jobRepository.save(job);
        System.out.println(job.toString());

        jobRepository.deleteById(job.getId());

        Job result = jobRepository.findById(job.getId()).orElse(null);

        Assertions.assertNull(result);
    }

    @Test
    public void RepositoryTest_findAll_JobList() {
        Iterable<Job> jobList = jobRepository.findAll();
        Assertions.assertNotNull(jobList);
        Assertions.assertEquals(3, jobList.spliterator().getExactSizeIfKnown());
    }

    @Test
    public void RepositoryTest_findByCategory_JobList() {
        Iterable<Job> jobList = jobRepository.findByCategory("Pantaloni");
        Assertions.assertNotNull(jobList);
        Assertions.assertEquals(1, jobList.spliterator().getExactSizeIfKnown());
    }

    @Test
    public void RepositoryTest_searchJob_JobList() {
        Iterable<Job> jobList = jobRepository.searchJob("keyword");
        Assertions.assertNotNull(jobList);
        Assertions.assertEquals(2, jobList.spliterator().getExactSizeIfKnown());
    }
}

package com.swe.sartoria.dao;

import com.swe.sartoria.model_domain.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    // write queries for methods as needed in here
    @Query("SELECT j FROM Job j WHERE j.name = ?1")
    List<Job> findJobByName(String name);

    @Query("SELECT j FROM Job j WHERE j.category = ?1")
    List<Job> findJobByCategory(String category);
}

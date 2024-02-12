package com.swe.sartoria.repository;

import com.swe.sartoria.model.Costumer;
import com.swe.sartoria.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    // write queries for methods as needed in here

    @Query("SELECT j FROM Job j WHERE j.name LIKE %?1%" +
            " OR j.description LIKE %?1%")
    List<Job> searchJob(String keyword);

    List<Job> findByCategory(String category);


}

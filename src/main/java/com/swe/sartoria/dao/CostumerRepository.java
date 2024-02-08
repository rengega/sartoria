package com.swe.sartoria.dao;

import com.swe.sartoria.model_domain.Costumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CostumerRepository extends JpaRepository<Costumer, Long> {
    // write queries for methods as needed in here

    @Query("SELECT c.id FROM Costumer c WHERE c.name = ?1")
    List<Long> findIdsByName(String name);
    @Query("SELECT c.id FROM Costumer c WHERE c.surname = ?1")
    List<Long> findIdsBySurname(String surname);


    // get id if name or surname contains string
    @Query("SELECT c.id FROM Costumer c WHERE c.name LIKE %?1%" +
            " OR c.surname LIKE %?1%")
    List<Long> findIdsByString(String searchString);

    @Query("SELECT c FROM Costumer c WHERE c.name LIKE %?1%" +
            " OR c.surname LIKE %?1%")
    List<Costumer> findCostumerByString(String searchString);

}

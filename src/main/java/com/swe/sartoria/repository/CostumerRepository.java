package com.swe.sartoria.repository;

import com.swe.sartoria.model.Costumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CostumerRepository extends JpaRepository<Costumer, Long> {


    // the following is a helper method for the search of orders in DAO
    @Query("SELECT c.id FROM Costumer c WHERE c.name LIKE %?1%" +
            " OR c.surname LIKE %?1%")
    List<Long> findIdsByString(String searchString);

    @Query("SELECT c FROM Costumer c WHERE c.name LIKE %?1%" +
            " OR c.surname LIKE %?1%")
    List<Costumer> findCostumerByString(String searchString);

}

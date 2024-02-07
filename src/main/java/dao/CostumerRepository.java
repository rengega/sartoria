package dao;

import model_domain.Costumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CostumerRepository extends JpaRepository<Costumer, Long> {
    // write queries for methods as needed in here


    @Query("SELECT c.id FROM Costumer c WHERE c.name = ?1")
    List<Long> findCostumerByName(String name);
    @Query("SELECT c.id FROM Costumer c WHERE c.surname = ?1")
    List<Long> findCostumerBySurname(String surname);
    @Query("SELECT c.id FROM Costumer c WHERE c.email = ?1")
    List<Long> findCostumerById(Long id);


}

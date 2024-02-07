package dao;

import model_domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // write queries for methods as needed in here
    @Query("SELECT o.id FROM Order o WHERE o.costumer.id = ?1")
    List<Long> findOrderByCostumer(long id);

}
package com.swe.sartoria.repository;

import com.swe.sartoria.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // write queries for methods as needed in here

    List<Order> findByCostumerId(long costumerId);
}

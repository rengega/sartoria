package com.swe.sartoria.repository;

import com.swe.sartoria.model.Account;
import com.swe.sartoria.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
    @Query("SELECT a FROM Account a JOIN a.user.roles r WHERE r.name = ?1")
    List<Account> findByUserRole(String role);

    @Query("SELECT a FROM Account a WHERE a.costumer.name LIKE %?1%" +" OR a.costumer.surname LIKE %?1%")
    List<Account>  searchByCostumerString(String search);

    @Query("SELECT a FROM Account a WHERE a.user.username LIKE ?1")
    List<Account> searchByUsername(String username);

    Account findByUser(UserEntity user);
}

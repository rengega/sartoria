package com.swe.sartoria.repository;

import com.swe.sartoria.model.Account;
import com.swe.sartoria.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

    Account findByUser(UserEntity user);
}

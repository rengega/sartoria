package com.swe.sartoria.repository;

import com.swe.sartoria.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
    List<Role> findByNameContaining(String name);
}
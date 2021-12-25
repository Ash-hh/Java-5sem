package com.shabunya.carrent.repository;

import com.shabunya.carrent.model.Role;
import com.shabunya.carrent.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByName(Role name);
}


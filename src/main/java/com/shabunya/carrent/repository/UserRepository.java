package com.shabunya.carrent.repository;

import com.shabunya.carrent.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    User findFirstByName(String name);
}

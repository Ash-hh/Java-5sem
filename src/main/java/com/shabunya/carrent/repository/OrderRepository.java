package com.shabunya.carrent.repository;

import com.shabunya.carrent.model.Order;
import com.shabunya.carrent.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    Order save(Order order);
    List<Order> findAllByUser (User user);

}

package com.shabunya.carrent.repository;

import com.shabunya.carrent.model.Order;
import com.shabunya.carrent.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    void delete(Order entity);
    Order save(Order order);
    List<Order> findAllByUser (User user);

}

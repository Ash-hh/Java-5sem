package com.shabunya.carrent.services;

import com.shabunya.carrent.exception.ControllerException;
import com.shabunya.carrent.model.Order;
import com.shabunya.carrent.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialException;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order create(Order order) throws SerialException, ControllerException {
        return orderRepository.save(order);
    }
}

package com.shabunya.carrent.services;

import com.shabunya.carrent.model.Order;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialException;

@Service
public interface OrderService {
    Order create(Order order) throws SerialException, com.shabunya.carrent.exception.ControllerException;
}

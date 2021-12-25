package com.shabunya.carrent.services;

import com.shabunya.carrent.dto.MakeOrderDTO;
import com.shabunya.carrent.model.Order;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialException;
import java.util.List;

@Service
public interface OrderService {
    Order createOrder(MakeOrderDTO makeOrderDTO, String UserLogin) throws SerialException, com.shabunya.carrent.exception.ControllerException;
    List<Order> userOrders(String UserLogin) throws SerialException, com.shabunya.carrent.exception.ControllerException;
}

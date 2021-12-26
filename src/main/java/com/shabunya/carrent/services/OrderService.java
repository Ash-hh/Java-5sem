package com.shabunya.carrent.services;

import com.shabunya.carrent.dto.MakeOrderDTO;
import com.shabunya.carrent.model.Order;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialException;
import java.util.List;
import java.util.Optional;

@Service
public interface OrderService {
    boolean deleteOrder(Long orderId);
    Order updateOrder(Order order);
    Optional<Order> findById(Long OrderId);
    Order createOrder(MakeOrderDTO makeOrderDTO, String UserLogin) throws SerialException, com.shabunya.carrent.exception.ControllerException;
    List<Order> userOrders(String UserLogin) throws SerialException, com.shabunya.carrent.exception.ControllerException;
}

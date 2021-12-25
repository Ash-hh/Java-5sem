package com.shabunya.carrent.services;

import com.shabunya.carrent.dto.MakeOrderDTO;
import com.shabunya.carrent.exception.ControllerException;
import com.shabunya.carrent.model.Car;
import com.shabunya.carrent.model.Order;
import com.shabunya.carrent.model.User;
import com.shabunya.carrent.repository.CarRepository;
import com.shabunya.carrent.repository.OrderRepository;
import com.shabunya.carrent.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialException;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Order createOrder(MakeOrderDTO makeOrderDTO, String UserLogin) throws SerialException, ControllerException {

        User user = userRepository.findByLogin(UserLogin);
        Car car = carRepository.getById(makeOrderDTO.carId);
        Order order = Order.builder()
                .car(car)
                .user(user)
                .dateEnd(Date.valueOf(makeOrderDTO.dateRentEnd))
                .dateStart(Date.valueOf(makeOrderDTO.dateRentStart))
                .sumrentcost(makeOrderDTO.sumRentCost)
                .build();
        System.out.println(order);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> userOrders(String UserLogin) throws SerialException, ControllerException {
        User user = userRepository.findByLogin(UserLogin);
        List<Order> orders = orderRepository.findAllByUser(user);
        return orders;
    }

}

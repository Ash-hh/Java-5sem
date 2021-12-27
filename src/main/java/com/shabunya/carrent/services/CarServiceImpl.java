package com.shabunya.carrent.services;

import com.shabunya.carrent.dto.CarDTOAll;
import com.shabunya.carrent.model.Car;
import com.shabunya.carrent.model.Order;
import com.shabunya.carrent.repository.CarRepository;
import com.shabunya.carrent.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService{

    private final CarRepository carRepository;

    private OrderRepository orderRepository;
    public CarServiceImpl(OrderRepository orderRepository, CarRepository carRepository) {
        this.orderRepository = orderRepository;
        this.carRepository = carRepository;
    }

    @Override
    public boolean save(Car car) {

        carRepository.save(car);
        return true;
    }

    @Override
    public List<Car> getAll() {
        return carRepository.findAll();
    }

    @Override
    public Car getCarById(Long id){
        return carRepository.getById(id);
    }

    @Override
    public void deleteCar(Long id) {
        Car car = carRepository.getById(id);
        List<Order> order = orderRepository.findAllByCar(car);
        orderRepository.deleteAll(order);
        carRepository.deleteById(id);
    }


}

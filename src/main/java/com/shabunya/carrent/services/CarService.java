package com.shabunya.carrent.services;

import com.shabunya.carrent.dto.CarDTOAll;
import com.shabunya.carrent.model.Car;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CarService {
    boolean save(Car car) ;
    List<Car> getAll();
    Car getCarById(Long id);
    void deleteCar(Long id);
}

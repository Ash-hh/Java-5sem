package com.shabunya.carrent.services;

import com.shabunya.carrent.dto.CarDTOAll;
import com.shabunya.carrent.model.Car;
import com.shabunya.carrent.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService{

    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
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
        carRepository.deleteById(id);
    }


}

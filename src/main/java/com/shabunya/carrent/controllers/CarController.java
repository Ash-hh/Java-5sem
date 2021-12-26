package com.shabunya.carrent.controllers;

import com.shabunya.carrent.dto.CarDTOAll;
import com.shabunya.carrent.dto.CarUpdateDTO;
import com.shabunya.carrent.dto.MakeOrderDTO;
import com.shabunya.carrent.exception.ControllerException;
import com.shabunya.carrent.model.Car;
import com.shabunya.carrent.repository.CarRepository;
import com.shabunya.carrent.services.CarService;
import com.shabunya.carrent.services.CarServiceImpl;
import com.shabunya.carrent.services.UserService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntToLongFunction;

@RestController
public class CarController {
    private static List<Car> cars = new ArrayList<Car>();

    @Autowired
    private static CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }


    @GetMapping("/getrentcar/{id}") //get car for rent by id
    public ResponseEntity<?> rentCar(@PathVariable(name="id")Long id) throws ControllerException {
        Car car = null;
        try{
            car = carService.getCarById(id);

            System.out.println(car);
            return new ResponseEntity<>(car, HttpStatus.OK);
        } catch(ServiceException e){
            throw new ControllerException(e);
        }

    }

    @PostMapping("/admin/updateCar")
    public ResponseEntity<?> updateCar(@RequestBody CarUpdateDTO newCar){
        Car car = carService.getCarById(newCar.getCarId());
        car.setCarName(newCar.getCarName());
        car.setType(newCar.getType());
        car.setCostPerDay(newCar.getCostPerDay());
        carService.save(car);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/admin/addCar")
    public ResponseEntity<?> addCar(@RequestBody CarDTOAll carToAdd){

        Car car = Car.builder()
                .carName(carToAdd.getCarName())
                .costPerDay(carToAdd.getCostPerDay())
                .type(carToAdd.getType())
                .build();
        carService.save(car);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/admin/deleteCar/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable(name="id")Long Id){

        carService.deleteCar(Id);

        return new ResponseEntity<>(HttpStatus.OK);
    }




}

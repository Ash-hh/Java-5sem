package com.shabunya.carrent.controllers;

import com.shabunya.carrent.dto.MakeOrderDTO;
import com.shabunya.carrent.exception.ControllerException;
import com.shabunya.carrent.model.Order;
import com.shabunya.carrent.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private static OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping("/makeorder")
    public ResponseEntity<?> makeOrderForRent(@RequestBody MakeOrderDTO makeOrderDTO) throws ControllerException{

        System.out.println(makeOrderDTO.toString());

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
